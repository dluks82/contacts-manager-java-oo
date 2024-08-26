package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.model.User;

import java.sql.*;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User newUser) throws SQLException {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newUser.getName());
            statement.setString(2, newUser.getLogin());
            statement.setString(3, newUser.getPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newUser.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }
}
