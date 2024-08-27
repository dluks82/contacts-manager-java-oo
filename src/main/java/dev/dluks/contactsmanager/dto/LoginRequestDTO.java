package dev.dluks.contactsmanager.dto;

public record LoginRequestDTO(String login, String password) {
    public LoginRequestDTO {
        if (login == null || login.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("login and password cannot be null or empty!");
        }
    }
}
