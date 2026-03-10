package com.securewallet.walletapi.dto;

import java.util.UUID;

public record UserResponseDTO(
        UUID externalId,
        String fullName,
        String email
) {}