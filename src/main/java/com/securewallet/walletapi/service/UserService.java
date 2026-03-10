package com.securewallet.walletapi.service;

import com.securewallet.walletapi.domain.User;
import com.securewallet.walletapi.dto.UserRegistrationRequestDTO;
import com.securewallet.walletapi.dto.UserResponseDTO;
import com.securewallet.walletapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRegistrationRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este e-mail já está cadastrado na nossa base."
            );
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User newUser = new User(
                request.fullName(),
                request.email(),
                hashedPassword
        );

        User savedUser = userRepository.save(newUser);
        
        return new UserResponseDTO(
                savedUser.getExternalId(),
                savedUser.getFullName(),
                savedUser.getEmail()
        );
    }
}