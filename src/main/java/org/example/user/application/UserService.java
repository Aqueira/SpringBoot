package org.example.user.application;


import org.example.user.dto.RequestUserDTO;
import org.example.user.dto.ResponseUserDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {
    ResponseUserDTO create(RequestUserDTO entity);

    ResponseUserDTO read(Long id);

    ResponseUserDTO update(Long id, RequestUserDTO entity);

    void delete(Long id);

    List<ResponseUserDTO> readAll();
}
