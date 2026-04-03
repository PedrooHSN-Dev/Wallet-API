package com.securewallet.walletapi.controller;

import com.securewallet.walletapi.domain.User;
import com.securewallet.walletapi.dto.TransferRequestDTO;
import com.securewallet.walletapi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @AuthenticationPrincipal User payer,
            @RequestBody @Valid TransferRequestDTO request) {

        transactionService.transferP2P(payer, request);
        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }
}