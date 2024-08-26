package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2InMemoryDatabase;
import dev.dluks.contactsmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    H2InMemoryDatabase db = new H2InMemoryDatabase();
    Connection connection;
    UserDAO sut;

    @BeforeEach
    public void setup() {
        try {
            connection = db.getConnection();
            DatabaseInitializer.execute(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sut = new UserDAO(connection);
    }

    @Test
    public void shouldBeAbleToAddNewUser() throws SQLException {
        User newUser = new User("Diogo", "diogo", "123");
        User newUser2 = new User("Diogo2", "diogo2", "123");

        sut.addUser(newUser);
        sut.addUser(newUser2);

        assertNotNull(newUser.getId());
        assertEquals(newUser2.getId().longValue(), 2L);

        assertEquals(newUser2.getName(), "Diogo2");
    }
}