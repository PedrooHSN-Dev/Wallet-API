package com.securewallet.walletapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDTO(
        @NotBlank @Size(max = 150) String fullName,
        @Email @NotBlank @Size(max = 255) String email,
        @NotBlank @Size(min = 8, max = 255, message = "A senha deve ter no mínimo 8 caracteres") String password
) {}
