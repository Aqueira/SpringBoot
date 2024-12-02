package org.example.user.dto;

import org.example.authentication.enums.Role;
import org.example.customer.dto.ResponseCustomerDTO;

public record ResponseUserDTO(Long id, String username, String password, Role role, ResponseCustomerDTO customer) {
}
