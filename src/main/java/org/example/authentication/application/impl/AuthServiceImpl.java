package org.example.authentication.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.authentication.application.AuthService;
import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.RequestRegistrationDTO;
import org.example.authentication.dto.ResponseLoginDTO;
import org.example.authentication.enums.Role;
import org.example.customer.domain.Customer;
import org.example.user.data.UserRepository;
import org.example.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JWTServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void register(RequestRegistrationDTO requestRegistrationDTO) {
        userRepository.save(User.builder()
                .username(requestRegistrationDTO.username())
                .password(bCryptPasswordEncoder.encode(requestRegistrationDTO.password()))
                .role(Role.USER)
                .customer(Customer.builder()
                        .name(requestRegistrationDTO.username())
                        .sector(requestRegistrationDTO.sector())
                        .build())
                .build());
    }

    @Override
    @Transactional
    public ResponseLoginDTO login(RequestLoginDTO requestLoginDTO) {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(requestLoginDTO.username(), requestLoginDTO.password()));
        return userRepository.findByUsername(requestLoginDTO.username())
                .map(user -> new ResponseLoginDTO(jwtService.generateToken(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
