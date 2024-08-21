package com.example.demo.domain;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(unique = true)
    private String profileName;

    public User() {}

    public User(UserRequestDTO userRequestDTO) {
        this.username = userRequestDTO.getUsername();
        this.password = userRequestDTO.getPassword();
    }

    public User hashPassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    UserResponseDTO toUserResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(this.id);
        userResponseDTO.setProfileName(this.profileName);
        return userResponseDTO;
    }
}
