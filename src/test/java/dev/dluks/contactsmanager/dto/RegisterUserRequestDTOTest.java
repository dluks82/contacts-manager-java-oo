package dev.dluks.contactsmanager.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserRequestDTOTest {

    @Test
    public void shouldThrowsIllegalArgumentExceptionWithEmptyOrNullFields() {
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO("", "diogo", "123"));
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO(null, "diogo", "123"));
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO("Diogo", "", "123"));
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO("Diogo", null, "123"));
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO("Diogo", "diogo", ""));
        assertThrows(IllegalArgumentException.class, () -> new RegisterUserRequestDTO("Diogo", "diogo", null));
    }

}