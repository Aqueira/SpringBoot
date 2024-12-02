package org.example.product.dto;

public record ResponseProductDTO(Long id, String productName, Integer quantity, Double price, Long lineItemId) {
}
