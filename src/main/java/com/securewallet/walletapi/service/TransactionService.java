package com.securewallet.walletapi.service;

import com.securewallet.walletapi.domain.User;
import com.securewallet.walletapi.domain.Wallet;
import com.securewallet.walletapi.dto.TransferRequestDTO;
import com.securewallet.walletapi.repository.UserRepository;
import com.securewallet.walletapi.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Transactional
    public void transferP2P(User payer, TransferRequestDTO request) {
        if (payer.getEmail().equals(request.payeeEmail())) {
            throw new IllegalArgumentException("Não pode transferir para si mesmo.");
        }

        User payee = userRepository.findByEmail(request.payeeEmail())
                .orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado."));

        // Busca as carteiras aplicando Lock Pessimista para evitar Race Conditions
        Wallet payerWallet = walletRepository.findByUserWithPessimisticLock(payer)
                .orElseThrow(() -> new IllegalStateException("Carteira do pagador não encontrada."));

        Wallet payeeWallet = walletRepository.findByUserWithPessimisticLock(payee)
                .orElseThrow(() -> new IllegalStateException("Carteira do recebedor não encontrada."));

        if (payerWallet.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        payerWallet.setBalance(payerWallet.getBalance().subtract(request.amount()));
        payeeWallet.setBalance(payeeWallet.getBalance().add(request.amount()));

        walletRepository.save(payerWallet);
        walletRepository.save(payeeWallet);
    }
}