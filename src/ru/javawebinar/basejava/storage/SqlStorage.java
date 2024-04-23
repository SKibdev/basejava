package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.execute(sql, r.getUuid(), (ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });

        for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
            sql = "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)";
            sqlHelper.<Void>execute(sql, ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                return null;
            });
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        String sql = "" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                "     WHERE r.uuid =? ";
        return sqlHelper.execute(sql, (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            } while (rs.next());

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        String sql = "DELETE FROM resume WHERE uuid =?";
        sqlHelper.execute(sql, (ps) -> {
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
        String sql = "SELECT * FROM resume ORDER BY full_name,uuid";

        return sqlHelper.execute(sql, (ps) -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                resumes.add(r);
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        LOG.info("size ");
        String sql = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(sql, (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
