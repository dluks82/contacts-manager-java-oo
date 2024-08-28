package dev.dluks.contactsmanager.dto;

public record LoginRequestDTO(String username, String password) {
    public LoginRequestDTO {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("username and password cannot be null or empty!");
        }
    }
}
