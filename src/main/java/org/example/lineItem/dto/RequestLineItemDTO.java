package org.example.lineItem.dto;

import org.example.product.dto.RequestProductDTO;

import java.util.List;

public record RequestLineItemDTO(Long orderId, List<RequestProductDTO> product) {
}
