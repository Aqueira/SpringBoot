
package org.example.order.controller;

import org.example.order.application.OrderService;
import org.example.order.application.impl.OrderServiceImpl;
import org.example.order.dto.RequestOrderDTO;
import org.example.order.dto.ResponseOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('management::write')")
    @PostMapping
    public ResponseEntity<ResponseOrderDTO> create(@RequestBody RequestOrderDTO requestOrderDTO) {
        return ResponseEntity.ok(orderService.create(requestOrderDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::read')")
    public ResponseEntity<ResponseOrderDTO> read(@PathVariable Long id) {
        return orderService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
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

