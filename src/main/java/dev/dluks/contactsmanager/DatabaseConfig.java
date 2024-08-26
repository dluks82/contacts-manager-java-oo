package dev.dluks.contactsmanager;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConfig {
    Connection getConnection() throws SQLException;
}
