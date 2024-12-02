package org.example.lineItem.dto;

import org.example.product.dto.ResponseProductDTO;

import java.util.List;

public record ResponseLineItemDTO(Long id, Long orderId, List<ResponseProductDTO> products) {
}
