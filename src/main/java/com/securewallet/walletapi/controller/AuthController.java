package com.securewallet.walletapi.controller;
import com.securewallet.walletapi.dto.LoginRequestDTO;
import com.securewallet.walletapi.dto.LoginResponseDTO;
import com.securewallet.walletapi.repository.UserRepository;
import com.securewallet.walletapi.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final JwtTokenProvider tokenProvider;

@PostMapping("/login")
public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
    var user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas."));

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
        throw new BadCredentialsException("Credenciais inválidas.");
    }

    String token = tokenProvider.generateToken(user.getEmail());
    return ResponseEntity.ok(new LoginResponseDTO(token));
}
}
