package dev.dluks.contactsmanager.dto;

public record AddContactRequestDTO(String name, String phone, String email, Long userId) {
}
