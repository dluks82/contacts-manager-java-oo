package dev.dluks.contactsmanager.db;

import java.sql.SQLException;

public class DatabaseInitializer {

    public static void execute(IDatabase database) throws SQLException {
        database.executeScript("src/db/schema.sql");
    }
}
