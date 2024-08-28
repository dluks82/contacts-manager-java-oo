package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2InMemoryDatabase;
import dev.dluks.contactsmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    H2InMemoryDatabase db = new H2InMemoryDatabase();
    Connection connection;
    UserDAO sut;

    @BeforeEach
    public void setup() {
        try {
            connection = db.getConnection();
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS users");
            }
            DatabaseInitializer.execute(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sut = new UserDAO(connection);
    }

    @Test
    public void shouldBeAbleToAddNewUser() throws SQLException {
        User newUser = new User("Diogo", "diogo", "123");
        Long userId = sut.save(newUser);
        assertEquals(userId, 1L);

        User user = sut.findById(userId);

        assertNotNull(user);
    }

    @Test
    public void shouldNotBeAbleToGetAnUser() throws SQLException {
        User newUser = new User("Diogo", "diogo", "123");
        Long userId = sut.save(newUser);
        assertEquals(userId, 1L);

        User user = sut.findById(2L);

        assertNull(user);
    }
}