package org.example.authentication.controller;


import org.example.authentication.application.AuthService;
import org.example.authentication.application.impl.AuthServiceImpl;
import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.RequestRegistrationDTO;
import org.example.authentication.dto.ResponseLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<ResponseLoginDTO> authentication(@RequestBody RequestLoginDTO requestLoginDTO) {
        return ResponseEntity.ok(authService.login(requestLoginDTO));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody RequestRegistrationDTO requestRegistrationDTO) {
        authService.register(requestRegistrationDTO);
        return ResponseEntity.ok().build();
    }
}
