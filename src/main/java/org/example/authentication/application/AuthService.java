package org.example.authentication.application;

import org.example.authentication.dto.RequestLoginDTO;
import org.example.authentication.dto.RequestRegistrationDTO;
import org.example.authentication.dto.ResponseLoginDTO;


public interface AuthService {
    void register(RequestRegistrationDTO registrationDTO);

    ResponseLoginDTO login(RequestLoginDTO loginDTO);
}
