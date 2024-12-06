package org.example.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.lineItem.domain.LineItem;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne(mappedBy = "product")
    @ToString.Exclude
    private LineItem lineItem;
}
