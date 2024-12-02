package org.example.user.dto;

import org.example.authentication.enums.Role;
import org.example.customer.dto.RequestCustomerDTO;

public record RequestUserDTO(String username, String password, Role role, RequestCustomerDTO customer) {
}
