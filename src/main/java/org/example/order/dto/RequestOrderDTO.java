package org.example.order.dto;

import org.example.lineItem.dto.RequestLineItemDTO;

import java.util.List;

public record RequestOrderDTO(String deliverTo, Long customerId, List<RequestLineItemDTO> lineItems) {
}
