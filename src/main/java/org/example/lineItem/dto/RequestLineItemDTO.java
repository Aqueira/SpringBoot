package org.example.lineItem.dto;

import org.example.product.domain.Product;

public record RequestLineItemDTO(Integer quantity, Product product) {}
