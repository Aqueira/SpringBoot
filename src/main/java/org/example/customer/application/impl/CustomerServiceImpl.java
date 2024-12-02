package org.example.customer.application.impl;

import org.example.customer.application.CustomerService;
import org.example.customer.data.CustomerRepository;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.customer.dto.ResponseCustomerDTO;
import org.example.customer.dto.mapper.ResponseCustomerMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseCustomerMapper responseCustomerMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ResponseCustomerMapper responseCustomerMapper,
                               RabbitTemplate rabbitTemplate) {
        this.customerRepository = customerRepository;
        this.responseCustomerMapper = responseCustomerMapper;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Transactional
    @Override
    public Optional<ResponseCustomerDTO> read(Long id) {
        return customerRepository.read(id).map(responseCustomerMapper::toDTO);
    }

    @Transactional
    @Override
    public void update(Long id, RequestCustomerDTO requestCustomerDTO) {
        customerRepository.update(requestCustomerDTO.name(), requestCustomerDTO.sector(), id);
        rabbitTemplate.convertAndSend("customers", "", id);
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
            throw new AuthenticationException("UNAUTHORIZED") {};
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        throw new AuthenticationException("UNAUTHORIZED"){};
    }
}
