package dev.dluks.contactsmanager.dto;

public record RegisterUserRequestDTO(String name, String login, String password) {
    public RegisterUserRequestDTO {

        if (name == null || name.trim().isEmpty() ||
                login == null || login.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("name, login and password cannot be null or empty!");
        }
    }
}
