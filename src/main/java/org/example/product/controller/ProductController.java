package org.example.product.controller;

import org.example.product.application.ProductService;
import org.example.product.dto.RequestProductDTO;
import org.example.product.dto.ResponseProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('management::write')")
    public ResponseEntity<ResponseProductDTO> create(@RequestBody RequestProductDTO requestProductDTO) {
        return ResponseEntity.ok(productService.create(requestProductDTO));
    }

    @PreAuthorize("hasAuthority('management::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> read(@PathVariable Long id) {
        return productService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('management::update')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RequestProductDTO requestProductDTO) {
        productService.update(id, requestProductDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('management::delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<ResponseProductDTO>> readAll() {
        return ResponseEntity.ok(productService.readAll());
    }
}
