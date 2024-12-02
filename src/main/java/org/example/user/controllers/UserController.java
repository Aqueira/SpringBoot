package org.example.user.controllers;


import org.example.user.application.UserService;
import org.example.user.dto.RequestUserDTO;
import org.example.user.dto.ResponseUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(@RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.ok(userService.create(requestUserDTO));
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('admin::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> read(@PathVariable Long id) {
        return userService.read(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('admin::update')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RequestUserDTO requestUserDTO) {
        userService.update(id, requestUserDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('admin::delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin::read')")
    public ResponseEntity<List<ResponseUserDTO>> readAll() {
        return ResponseEntity.ok(userService.readAll());
    }
}

