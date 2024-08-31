package dev.dluks.contactsmanager.dto;

public record RegisterUserResponseDTO(boolean created, Long id, String errorMessage) {

}
