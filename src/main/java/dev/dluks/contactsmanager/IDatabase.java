package dev.dluks.contactsmanager;

import java.sql.SQLException;

public interface IDatabase {
    void connect() throws SQLException;

    void disconnect() throws SQLException;

    boolean isConnected();

    void executeScript(String script) throws SQLException;
}
