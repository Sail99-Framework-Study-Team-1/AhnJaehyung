package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void postUser_usernameValidation_tooLong() {
        userRequestDTO.setUsername("23il4jil23j4il23j4li2j3il4j23li4j2il3j4l23j4il");
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void postUser_usernameValidation_nonAlphanumericCase() {
        userRequestDTO.setUsername("ajf234SSDE");
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void postUser_passwordValidation_tooShort() {
        userRequestDTO.setPassword("shpw");
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void postUser_passwordValidation_tooLong() {
        userRequestDTO.setPassword("tooooooOOOOOoLl1ooo000ngpassw0rd");
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void postUser_passwordValidation_nonAlphanumeric() {
        userRequestDTO.setPassword("asdflidajsd;lf@@!!!!@#$%");
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.save(userRequestDTO)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}