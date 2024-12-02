package org.example.lineItem.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.order.domain.Order;
import org.example.product.domain.Product;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineItem")
    private List<Product> product;
}
