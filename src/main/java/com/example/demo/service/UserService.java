package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;
    private final Pattern usernamePattern = Pattern.compile("^[a-z0-9]{4,10}$");

    private final Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9]{8,15}$");
    public User save(UserRequestDTO userRequestDTO) {
        Matcher usernameMatcher = usernamePattern.matcher(userRequestDTO.getUsername());
        Matcher passwordMatcher = passwordPattern.matcher(userRequestDTO.getPassword());

        if (!usernameMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username");
        }

        if (!passwordMatcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User(userRequestDTO).hashPassword(passwordEncoder);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
