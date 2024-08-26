package dev.dluks.contactsmanager.db;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2FileDatabase implements IDatabase {
    private Connection connection;

    private static final String JDBC_URL = "jdbc:h2:file:./data/contactsmanager;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "password";

    @Override
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    JDBC_URL, JDBC_USER, JDBC_PASSWORD
            );
            System.out.println("Connected to the database.");
        }
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void executeScript(String filePath) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
                 Statement statement = connection.createStatement()) {

                StringBuilder scriptBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    scriptBuilder.append(line).append("\n");
                }

                String[] sqlStatements = scriptBuilder.toString().split(";");
                for (String sql : sqlStatements) {
                    if (!sql.trim().isEmpty()) {
                        statement.execute(sql.trim());
                    }
                }
                System.out.println("Script executed successfully.");
            } catch (IOException e) {
                throw new SQLException("Failed to read script: " + filePath, e);
            }
        } else {
            System.out.println("Not connected to the database.");
        }
    }

    public Connection getConnection() throws SQLException {
        this.connect();
        return connection;
    }
}
