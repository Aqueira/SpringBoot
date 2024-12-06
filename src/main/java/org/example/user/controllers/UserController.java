package org.example.user.controllers;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(@RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.ok(userService.create(requestUserDTO));
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('admin::read')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @PreAuthorize("hasRole('USER') and #id == authentication.principal.id or hasAuthority('admin::update')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long id, @RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.ok(userService.update(id, requestUserDTO));
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

