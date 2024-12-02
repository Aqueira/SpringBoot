package org.example.order.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.customer.domain.Customer;
import org.example.lineItem.domain.LineItem;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "deliver_to", nullable = false, length = 100)
    private String deliverTo;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<LineItem> lineItems;
}
