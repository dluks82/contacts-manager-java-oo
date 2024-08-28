package dev.dluks.contactsmanager.dto;

public record LoginResponseDTO(boolean logged, Long id, String name, String errorMessage) {
}
