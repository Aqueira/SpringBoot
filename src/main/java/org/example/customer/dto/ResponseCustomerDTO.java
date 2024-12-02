package org.example.customer.dto;

import org.example.order.dto.ResponseOrderDTO;

import java.util.List;

public record ResponseCustomerDTO(Long id, String name, String sector, List<ResponseOrderDTO> orders) {
}
