package dev.dluks.contactsmanager.dto;

public record RegisterUserRequestDTO(String name, String username, String password) {
    public RegisterUserRequestDTO {

        if (name == null || name.trim().isEmpty() ||
                username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("name, username and password cannot be null or empty!");
        }
    }
}
