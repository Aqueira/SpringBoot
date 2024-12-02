package org.example.user.application.impl;

import org.example.user.application.UserService;
import org.example.user.data.UserRepository;
import org.example.user.dto.RequestUserDTO;
import org.example.user.dto.ResponseUserDTO;
import org.example.user.dto.mapper.RequestUserMapper;
import org.example.user.dto.mapper.ResponseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RequestUserMapper requestUserMapper;
    private final ResponseUserMapper responseUserMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RequestUserMapper requestUserMapper,
                           ResponseUserMapper responseUserMapper) {
        this.userRepository = userRepository;
        this.requestUserMapper = requestUserMapper;
        this.responseUserMapper = responseUserMapper;
    }

    @Transactional
    @Override
    public ResponseUserDTO create(RequestUserDTO entity) {
        return responseUserMapper.toDTO(userRepository.save(requestUserMapper.toEntity(entity)));
    }

    @Transactional
    @Override
    @Cacheable(value = "userCache", key = "#id")
    public ResponseUserDTO read(Long id) {
        return userRepository.read(id)
                .map(responseUserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "userCache", key = "#id"),
            @CacheEvict(value = "customerCache", key = "#id")
    })
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    @CachePut(value = "userCache", key = "#id")
    public ResponseUserDTO update(Long id, RequestUserDTO requestUserDTO) {
        userRepository.update(id, requestUserDTO.username(), requestUserDTO.password(), requestUserDTO.role());
        return userRepository.read(id)
                .map(responseUserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Transactional
    @Override
    public List<ResponseUserDTO> readAll() {
        return responseUserMapper.toDTOs(userRepository.readAll());
    }
}

