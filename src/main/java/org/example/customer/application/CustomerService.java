package org.example.customer.application;

import org.example.customer.dto.RequestCustomerDTO;
import org.example.customer.dto.ResponseCustomerDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface CustomerService {
    Optional<ResponseCustomerDTO> read(Long id);

    void update(Long id, RequestCustomerDTO entity);

    List<ResponseCustomerDTO> findAll();

    UserDetails getCurrent();
}

