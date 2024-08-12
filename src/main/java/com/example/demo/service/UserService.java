package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    @Autowired UserRepository userRepository;

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

        User user = new User(userRequestDTO);
        return userRepository.save(user);
    }
}
