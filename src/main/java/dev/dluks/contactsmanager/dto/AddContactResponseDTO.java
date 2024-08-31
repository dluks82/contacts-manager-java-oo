package dev.dluks.contactsmanager.dto;

public record AddContactResponseDTO(boolean created, Long id, String errorMessage) {
}
