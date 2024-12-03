package org.example.customer.application.impl;

import org.example.configurations.rabbitmq.RabbitMQService;
import org.example.customer.application.CustomerService;
import org.example.customer.data.CustomerRepository;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.customer.dto.ResponseCustomerDTO;
import org.example.customer.dto.mapper.ResponseCustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseCustomerMapper responseCustomerMapper;
    private final RabbitMQService rabbitMQService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ResponseCustomerMapper responseCustomerMapper,
                               RabbitMQService rabbitMQService) {
        this.customerRepository = customerRepository;
        this.responseCustomerMapper = responseCustomerMapper;
        this.rabbitMQService = rabbitMQService;
    }


    @Transactional
    @Override
    @Cacheable(value = "customerCache", key = "#id")
    public ResponseCustomerDTO read(Long id) {
        return customerRepository.read(id)
                .map(responseCustomerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Transactional
    @Override
    @CachePut(value = "customerCache", key = "#id")
    public ResponseCustomerDTO update(Long id, RequestCustomerDTO requestCustomerDTO) {
        customerRepository.update(requestCustomerDTO.name(), requestCustomerDTO.sector(), id);
        rabbitMQService.sendInvalidationEvent(id);
        return customerRepository.read(id)
                .map(responseCustomerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Update customer FAILED!"));
    }

    @Transactional
    @Override
    public List<ResponseCustomerDTO> findAll() {
        return responseCustomerMapper.toDTOs(customerRepository.readAll());

    }

    @Override
    public UserDetails getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("UNAUTHORIZED") {
            };
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        throw new AuthenticationException("UNAUTHORIZED") {
        };
    }
}
