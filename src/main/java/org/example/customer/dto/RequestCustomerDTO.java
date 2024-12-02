package org.example.customer.dto;


import org.example.order.dto.RequestOrderDTO;

import java.util.List;

public record RequestCustomerDTO(String name, String sector, List<RequestOrderDTO> orders) {
}
