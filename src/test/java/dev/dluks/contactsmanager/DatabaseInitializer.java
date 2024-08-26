package dev.dluks.contactsmanager;

import java.sql.SQLException;

public class DatabaseInitializer {

    public static void execute(IDatabase database) throws SQLException {
        database.executeScript("schema.sql");
        database.executeScript("data.sql");
    }
}
