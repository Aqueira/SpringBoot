package org.example.order.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.customer.domain.Customer;
import org.example.order.application.OrderService;
import org.example.order.application.impl.OrderServiceImpl;
import org.example.order.dto.RequestOrderDTO;
import org.example.order.dto.ResponseOrderDTO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @PreAuthorize("hasRole('USER') or hasAuthority('management::write')")
    @PostMapping
    public ResponseEntity<ResponseOrderDTO> create(@RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseEntity.ok(orderService.create(requestOrderDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::read')")
    public ResponseEntity<ResponseOrderDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.read(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::update')")
    public ResponseEntity<ResponseOrderDTO> update(@PathVariable Long id, @RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseEntity.ok(orderService.update(id, requestOrderDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('management::read')")
    public ResponseEntity<List<ResponseOrderDTO>> readAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
}

