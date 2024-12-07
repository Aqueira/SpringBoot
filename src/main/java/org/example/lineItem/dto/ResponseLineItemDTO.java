package org.example.lineItem.dto;


import org.example.product.dto.ResponseProductDTO;

public record ResponseLineItemDTO(Long id, Long orderId, Integer quantity, ResponseProductDTO product) {}
