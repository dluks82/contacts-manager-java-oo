package dev.dluks.contactsmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class DatabaseTest {

    @Test
    public void initialize() {
        IDatabase db = new H2InMemoryDatabase();
        try {
            db.connect();
            DatabaseInitializer.execute(db);
            assertTrue(db.isConnected());
            db.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}