package org.example.product.dto;

public record RequestProductDTO(String productName, Integer quantity, Double price, Long lineItemId) {
}
