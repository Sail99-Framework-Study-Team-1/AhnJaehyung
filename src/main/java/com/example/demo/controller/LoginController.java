package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        User user = userService.save(userRequestDTO);
        return ResponseEntity.ok(user.getUsername());
    }
}
