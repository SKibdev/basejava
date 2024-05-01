package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            Connection conn;
            try {
                Class.forName("org.postgresql.Driver"); // Загрузка драйвера PostgreSQL
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            } catch (ClassNotFoundException e) {
                throw new StorageException("Драйвер JDBC для PostgreSQL не найден.", e);
            } catch (SQLException e) {
                throw new StorageException("Ошибка при подключении к базе данных PostgreSQL.", e);
            }
            return conn;
        });
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);

        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteTable(r, conn, "DELETE FROM contact WHERE resume_uuid =?");
            deleteTable(r, conn, "DELETE FROM section WHERE resume_uuid =?");
            insertData(r, conn, "contact");
            insertData(r, conn, "section");
            return null;
        });
    }

    private static void deleteTable(Resume r, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertData(r, conn, "contact");
            insertData(r, conn, "section");
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps =
                         conn.prepareStatement("" +
                                 "    SELECT * FROM resume r " +
                                 " LEFT JOIN contact c " +
                                 "        ON r.uuid = c.resume_uuid " +
                                 "     WHERE r.uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(r, rs);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(r, rs);
                }
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");

        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    map.put(uuid, r);
                }
            }
            addDataFromTable(conn, map, "contact");
            addDataFromTable(conn, map, "section");
            return new ArrayList<>(map.values());
        });
    }

    private void addDataFromTable(Connection conn, Map<String, Resume> map, String nameTable) throws SQLException {
        String sql = "SELECT * FROM " + nameTable;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resumeUuid = rs.getString("resume_uuid");

                for (Map.Entry<String, Resume> entry : map.entrySet()) {
                    if (resumeUuid.equals(entry.getKey())) {
                        addDataToResume(entry.getValue(), rs, nameTable);
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        LOG.info("size ");
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertData(Resume r, Connection conn, String tableName) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (resume_uuid, type, value) VALUES (?,?,?)";

        try (PreparedStatement ps =
                     conn.prepareStatement(sql)) {
            for (Map.Entry<?, ?> entry : (tableName.equals("contact") ? r.getContacts() : r.getSections()).entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().toString());
                ps.setString(3, entry.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addDataToResume(Resume r, ResultSet rs, String nameTable) throws SQLException {
        if (nameTable.equals("contact")) {
            addContact(r, rs);
        } else if (nameTable.equals("section")) {
            addSection(r, rs);
        }
    }

    private void addContact(Resume r, ResultSet rs) throws SQLException {
        String contactType = rs.getString("type");
        if (contactType != null) {
            String value = rs.getString("value");
            ContactType type = ContactType.valueOf(contactType);
            r.addContact(type, value);
        }
    }

    private void addSection(Resume r, ResultSet rs) throws SQLException {
        String sectionType = rs.getString("type");
        if (sectionType != null) {
            SectionType type = SectionType.valueOf(sectionType);
            String section_value = rs.getString("value");
            Section section;
            switch (type) {
                case PERSONAL, OBJECTIVE -> section = new TextSection(section_value);
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> items = Arrays.asList(section_value.split("\n"));
                    section = new ListSection(items);
                }
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
            r.addSection(type, section);
        }
    }
}
