package com.example.demo.domain;

import com.example.demo.dto.UserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    public User() {}

    public User(UserRequestDTO userRequestDTO) {
        this.username = userRequestDTO.getUsername();
        this.password = userRequestDTO.getPassword();
    }
}
