package dev.dluks.contactsmanager.dao;

import dev.dluks.contactsmanager.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private final Connection connection;

    public ContactDAO(Connection connection) {
        this.connection = connection;
    }

    public Long save(Contact newContact) throws SQLException {
        String sql = "INSERT INTO contacts (name, phone, email, user_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newContact.getName());
            statement.setString(2, newContact.getPhone());
            statement.setString(3, newContact.getEmail());
            statement.setLong(4, newContact.getUser_id());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        return -1L;
                    }
                }
            } else {
                return -1L;
            }
        }
    }

    public int remove(Contact contact) {
        String sql = "DELETE FROM contacts WHERE id = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, contact.getId());
            stmt.setLong(2, contact.getUser_id());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ? WHERE id = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getPhone());
            stmt.setString(3, contact.getEmail());
            stmt.setLong(4, contact.getId());
            stmt.setLong(5, contact.getUser_id());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating contact: " + e.getMessage(), e);
        }
    }


    public Contact findByPhoneAndUserId(String phone, Long userId) {
        String sql = "SELECT * FROM contacts WHERE phone = ?  AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);
            stmt.setLong(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contact(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("user_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: refactor in future
        }
        return null;
    }

    public Contact findByEmailAndUserId(String email, Long userId) {
        String sql = "SELECT * FROM contacts WHERE email = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setLong(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contact(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("user_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: refactor in future
        }
        return null;
    }

    public Contact findById(Long id, Long userId) {
        String sql = "SELECT * FROM contacts WHERE id = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.setLong(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contact(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("user_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: refactor in future
        }
        return null;
    }

    public List<Contact> findAll(Long userId) {
        String sql = "SELECT * FROM contacts WHERE user_id = ?";
        List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contact contact = new Contact(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getLong("user_id")
                    );
                    contacts.add(contact);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: refactor in future
        }

        return contacts;
    }

}
