package org.example.customer.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.configurations.rabbitmq.RabbitMQService;
import org.example.customer.application.CustomerService;
import org.example.customer.data.CustomerRepository;
import org.example.customer.dto.CurrentDTO;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.customer.dto.ResponseCustomerDTO;
import org.example.customer.dto.mapper.ResponseCustomerMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseCustomerMapper responseCustomerMapper;
    private final RabbitMQService rabbitMQService;

    @Transactional
    @Override
    @Cacheable(value = "customerCache", key = "#id")
    public ResponseCustomerDTO read(Long id) {
        return customerRepository.read(id)
                .map(responseCustomerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));
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
    public CurrentDTO getCurrent(UserDetails userDetails) {
       return new CurrentDTO(
               userDetails.getAuthorities(),
               userDetails.isAccountNonExpired(),
               userDetails.isAccountNonLocked(),
               userDetails.isEnabled(),
               userDetails.getUsername()
       );
    }
}
