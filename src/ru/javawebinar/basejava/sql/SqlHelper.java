package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper() {
        connectionFactory = () -> DriverManager
                .getConnection(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword());
    }

    public <T> void execute(String sql, String uuid, SqlConnector<T> connector) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            connector.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }

    public <T> T execute(String sql, SqlConnector<T> connector) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return connector.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
