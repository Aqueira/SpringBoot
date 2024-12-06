package org.example.product.data;

import org.example.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product product " +
            "SET product.productName = :name, product.price = :price " +
            "WHERE product.id = :id")
    void update(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("price") Double price
    );
}
