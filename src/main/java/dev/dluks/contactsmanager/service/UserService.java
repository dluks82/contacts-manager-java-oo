package dev.dluks.contactsmanager.service;

import dev.dluks.contactsmanager.dao.UserDAO;
import dev.dluks.contactsmanager.dto.LoginRequestDTO;
import dev.dluks.contactsmanager.dto.LoginResponseDTO;
import dev.dluks.contactsmanager.dto.RegisterUserRequestDTO;
import dev.dluks.contactsmanager.dto.RegisterUserResponseDTO;
import dev.dluks.contactsmanager.model.User;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public RegisterUserResponseDTO register(RegisterUserRequestDTO data) {
        try {

            if (userDAO.findByLogin(data.username()) != null) {
                return new RegisterUserResponseDTO(false, "User already exists!");
            }

            User newUser = new User(data.name(), data.username(), data.password());
            return new RegisterUserResponseDTO(userDAO.registerUser(newUser), null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        User user = userDAO.findByLogin(data.username());
        if (user != null && user.passwordMatch(data.password())) {
            return new LoginResponseDTO(true, user.getId(), user.getName(), null);
        }
        return new LoginResponseDTO(false, null, null, "Invalid username or password!");
    }
}
