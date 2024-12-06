package org.example.lineItem.dto;


public record ResponseLineItemDTO(Long id, Long orderId, Integer quantity, Long productId) {}
