package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2InMemoryDatabase;
import dev.dluks.contactsmanager.model.Contact;
import dev.dluks.contactsmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDAOTest {
    H2InMemoryDatabase db = new H2InMemoryDatabase();
    Connection connection;
    UserDAO userDAO;

    ContactDAO sut;

    @BeforeEach
    public void setup() {
        try {
            connection = db.getConnection();
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS contacts");
                stmt.execute("DROP TABLE IF EXISTS users");
            }
            DatabaseInitializer.execute(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userDAO = new UserDAO(connection);
        sut = new ContactDAO(connection);
    }

    @Test
    public void shouldBeAbleToAddNewContact() throws SQLException {
        User newUser = new User("Diogo", "diogo", "123");
        Long userId = userDAO.save(newUser);

        Contact newContact = new Contact("Novo contato", "4141", "dluks82@email", userId);

        Long contactId = sut.save(newContact);
        assertEquals(1L, contactId);
    }

    @Test
    public void shouldBeAbleToGetAllContactsBelongsUser() throws SQLException {
        User newUser = new User("Diogo", "diogo", "123");
        Long userId = userDAO.save(newUser);

        Contact contact1 = new Contact("Novo contato 1", "4141", "dluks82@email", userId);
        Contact contact2 = new Contact("Novo contato 2", "4141", "dluks82@email", userId);

        sut.save(contact1);
        sut.save(contact2);

        List<Contact> myContacts = sut.findAll(userId);
        assertEquals(2, myContacts.size());
    }
}