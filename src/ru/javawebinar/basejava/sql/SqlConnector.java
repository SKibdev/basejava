package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlConnector<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
