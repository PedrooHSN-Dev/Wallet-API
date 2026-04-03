package com.securewallet.walletapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequestDTO(
        @NotNull String payeeEmail,
        @NotNull @DecimalMin(value = "0.01", message = "O valor mínimo é 0.01") BigDecimal amount
) {
}