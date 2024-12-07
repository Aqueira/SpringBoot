package org.example.lineItem.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.order.domain.Order;
import org.example.product.domain.Product;

@Entity
@Data
@Table(name = "line_items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ToString.Exclude
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
