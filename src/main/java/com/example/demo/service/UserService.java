package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Transactional
@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int join(User user) throws Exception {
        if(user.getEmail() == null) throw new Exception("Email doesn't exist");
        if(user.getUsername() == null) throw new Exception("Username doesn't exist");
        if(user.getPassword() == null) throw new Exception("Password doesn't exist");

        user.setCreatedAt(new Date());

        return userRepository.save(user).getId();
    }
}
