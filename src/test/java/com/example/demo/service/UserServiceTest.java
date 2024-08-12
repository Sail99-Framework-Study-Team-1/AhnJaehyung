package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;

    UserRequestDTO userRequestDTO = new UserRequestDTO();

    @BeforeEach
    void setUp() {
        this.userRequestDTO.setUsername("username");
        this.userRequestDTO.setPassword("password");
    }

    @Test
    void postUser() {
        assertDoesNotThrow( () -> userService.save(userRequestDTO) );
    }

    @Test
    void postUser_usernameValidation_tooShort() {
        userRequestDTO.setUsername("un");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }

    @Test
    void postUser_usernameValidation_tooLong() {
        userRequestDTO.setUsername("23il4jil23j4il23j4li2j3il4j23li4j2il3j4l23j4il");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }

    @Test
    void postUser_usernameValidation_nonAlphanumericCase() {
        userRequestDTO.setUsername("ajf234SSDE");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }

    @Test
    void postUser_passwordValidation_tooShort() {
        userRequestDTO.setPassword("shpw");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }

    @Test
    void postUser_passwordValidation_tooLong() {
        userRequestDTO.setPassword("tooooooOOOOOoLl1ooo000ngpassw0rd");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }

    @Test
    void postUser_passwordValidation_nonAlphanumeric() {
        userRequestDTO.setPassword("asdflidajsd;lf@@!!!!@#$%");
        assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );
    }
}