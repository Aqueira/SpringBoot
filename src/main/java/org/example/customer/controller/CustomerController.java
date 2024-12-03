package org.example.customer.controller;


import org.example.customer.application.CustomerService;
import org.example.customer.application.impl.CustomerServiceImpl;
import org.example.customer.dto.RequestCustomerDTO;
import org.example.customer.dto.ResponseCustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('USER') and #id == authentication.principal.id or hasAuthority('management::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCustomerDTO> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.read(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('management::read')")
    public ResponseEntity<List<ResponseCustomerDTO>> readAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('management::update')")
    public ResponseEntity<ResponseCustomerDTO> update(@PathVariable Long id, @RequestBody RequestCustomerDTO requestCustomerDTO) {
        return ResponseEntity.ok(customerService.update(id, requestCustomerDTO));
    }

    @GetMapping("/current")
    public ResponseEntity<UserDetails> current() {
        return ResponseEntity.ok(customerService.getCurrent());
    }
}
