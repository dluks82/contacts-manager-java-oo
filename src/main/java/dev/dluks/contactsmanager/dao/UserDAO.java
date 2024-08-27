package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.model.User;

import java.sql.*;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(User newUser) throws SQLException {
        String sql = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newUser.getName());
            statement.setString(2, newUser.getLogin());
            statement.setString(3, newUser.getPasswordHash());

            int affectedRows = statement.executeUpdate();

            return affectedRows > 0;
        }
    }

    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: refactor in future
        }
        return null;
    }
}
