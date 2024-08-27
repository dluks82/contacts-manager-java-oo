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
            User newUser = new User(data.name(), data.login(), data.password());
            return new RegisterUserResponseDTO(userDAO.registerUser(newUser));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        User user = userDAO.findByLogin(data.login());
        if (user != null && user.passwordMatch(data.password())) {
            return new LoginResponseDTO(user.getId(), user.getName());
        }
        return null;
    }
}
