package dev.dluks.contactsmanager.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    @Test
    public void shouldThrowsIllegalArgumentExceptionWithEmptyOrNullFields() {
        assertThrows(IllegalArgumentException.class, () -> new LoginRequestDTO("", "123"));
        assertThrows(IllegalArgumentException.class, () -> new LoginRequestDTO(null, "123"));
        assertThrows(IllegalArgumentException.class, () -> new LoginRequestDTO("diogo", ""));
        assertThrows(IllegalArgumentException.class, () -> new LoginRequestDTO("diogo", null));
    }

}