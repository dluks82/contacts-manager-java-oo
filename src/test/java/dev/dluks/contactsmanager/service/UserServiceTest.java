package dev.dluks.contactsmanager.service;

import dev.dluks.contactsmanager.dao.UserDAO;
import dev.dluks.contactsmanager.db.DatabaseInitializer;
import dev.dluks.contactsmanager.db.H2InMemoryDatabase;
import dev.dluks.contactsmanager.dto.LoginRequestDTO;
import dev.dluks.contactsmanager.dto.LoginResponseDTO;
import dev.dluks.contactsmanager.dto.RegisterUserRequestDTO;
import dev.dluks.contactsmanager.dto.RegisterUserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    H2InMemoryDatabase db = new H2InMemoryDatabase();
    Connection connection;
    UserDAO userDAO;

    UserService sut;

    @BeforeEach
    public void setup() {
        try {
            connection = db.getConnection();

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS contacts");
                stmt.execute("DROP TABLE IF EXISTS users");
            }

            DatabaseInitializer.execute(db);

            userDAO = new UserDAO(connection);
            sut = new UserService(userDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldBeAbleToRegisterNewUser() {
        RegisterUserRequestDTO newUser = new RegisterUserRequestDTO("Diogo", "diogo", "123");
        RegisterUserResponseDTO response = sut.register(newUser);
        assertTrue(response.created());
        assertEquals(1L, response.id());
    }

    @Test
    public void shouldNotBeAbleToRegisterNewUserWithSameUsername() {
        RegisterUserRequestDTO newUser = new RegisterUserRequestDTO("Diogo", "diogo", "123");
        RegisterUserRequestDTO newUser_dup = new RegisterUserRequestDTO("Other Diogo", "diogo", "123");

        RegisterUserResponseDTO response = sut.register(newUser);
        RegisterUserResponseDTO response_dup = sut.register(newUser_dup);

        assertFalse(response_dup.created());
    }

    @Test
    public void shouldBeAbleToLogin() {
        RegisterUserRequestDTO newUser = new RegisterUserRequestDTO("Diogo", "diogo", "123");
        sut.register(newUser);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("diogo", "123");

        LoginResponseDTO loginResponseDTO = sut.login(loginRequestDTO);

        assertNotNull(loginResponseDTO);
        assertEquals(loginResponseDTO.name(), newUser.name());
        assertEquals(loginResponseDTO.id(), 1L);
    }

    @Test
    public void shouldNotBeAbleToLoginWithInvalidLogin() {
        RegisterUserRequestDTO newUser = new RegisterUserRequestDTO("Diogo", "diogo", "123");

        String invalidLogin = "wrongLogin";
        sut.register(newUser);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(invalidLogin, "123");

        LoginResponseDTO loginResponseDTO = sut.login(loginRequestDTO);

        assertFalse(loginResponseDTO.logged());
    }

    @Test
    public void shouldNotBeAbleToLoginWithWrongPassword() {
        RegisterUserRequestDTO newUser = new RegisterUserRequestDTO("Diogo", "diogo", "123");

        String wrongPassword = "wrongPassword";
        sut.register(newUser);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("diogo", wrongPassword);

        LoginResponseDTO loginResponseDTO = sut.login(loginRequestDTO);

        assertFalse(loginResponseDTO.logged());
    }
}