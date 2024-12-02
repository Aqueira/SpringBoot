package org.example.order.dto;

import org.example.lineItem.dto.ResponseLineItemDTO;

import java.util.List;

public record ResponseOrderDTO(Long id, String deliverTo, Long customerId, List<ResponseLineItemDTO> lineItems) {
}
