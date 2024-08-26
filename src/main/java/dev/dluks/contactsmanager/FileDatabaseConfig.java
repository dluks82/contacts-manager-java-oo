package dev.dluks.contactsmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FileDatabaseConfig implements DatabaseConfig {

    private static final String JDBC_URL = "jdbc:h2:file:./data/contactsmanager;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "password";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                JDBC_URL, JDBC_USER, JDBC_PASSWORD
        );
    }
}