package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void join() {
        // Given
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123456");
        user1.setEmail("test@gmail.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123456");
        user2.setEmail("test2@gmail.com");

        // When

        // Then
        assertDoesNotThrow(() -> {
            int user1ID = userService.join(user1);
            int user2ID = userService.join(user2);
            System.out.println("Created User ID : " + user1ID);
            System.out.println("Created User ID : " + user2ID);
        });
    }

    @Test
    void join_duplicate_email() throws Exception {
        // Given
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123456");
        user1.setEmail("test@gmail.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123456");
        user2.setEmail("test@gmail.com");

        // When

        // Then
        userService.join(user1);
        assertThrows(Exception.class, () -> userService.join(user2));
    }
}