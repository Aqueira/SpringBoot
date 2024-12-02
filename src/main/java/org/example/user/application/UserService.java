package org.example.user.application;


import org.example.user.dto.RequestUserDTO;
import org.example.user.dto.ResponseUserDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {
    ResponseUserDTO create(RequestUserDTO entity);

    Optional<ResponseUserDTO> read(Long id);

    void update(Long id, RequestUserDTO entity);

    void delete(Long id);

    List<ResponseUserDTO> readAll();
}
