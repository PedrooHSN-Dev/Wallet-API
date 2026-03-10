package com.securewallet.walletapi.controller;

import com.securewallet.walletapi.dto.UserRegistrationRequestDTO;
import com.securewallet.walletapi.dto.UserResponseDTO;
import com.securewallet.walletapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO request) {


        UserResponseDTO responseDTO = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}