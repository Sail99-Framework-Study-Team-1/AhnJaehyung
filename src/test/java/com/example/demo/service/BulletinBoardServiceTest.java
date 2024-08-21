package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
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
class BulletinBoardServiceTest {

    private final BulletinBoardRequestDTO bulletinBoardRequestDTO1 = new BulletinBoardRequestDTO();
    private final BulletinBoardRequestDTO bulletinBoardRequestDTO2 = new BulletinBoardRequestDTO();

    @Autowired private BulletinBoardService bulletinBoardService;
    @Autowired private UserService userService;

    @BeforeEach
    void setUp() {
        DemoApplicationTests.loginTestUser(userService, 1);

        this.bulletinBoardRequestDTO1.setTitle("Title1");
        this.bulletinBoardRequestDTO1.setContent("Content1");

        this.bulletinBoardRequestDTO2.setTitle("Title2");
        this.bulletinBoardRequestDTO2.setContent("Content2");
    }

    @Test
    void putBulletinBoard() throws Exception {
        BulletinBoard bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO1);

        bulletinBoardRequestDTO1.setTitle("Title2");
        bulletinBoardRequestDTO1.setContent("Content2");

        BulletinBoard changedBulletinBoard = bulletinBoardService.putBulletinBoard(
                bulletinBoard.getId(),
                bulletinBoardRequestDTO1
        );

        assertEquals(changedBulletinBoard.getTitle(), "Title2");
        assertEquals(changedBulletinBoard.getContent(), "Content2");
    }

    @Test
    void putBulletinBoardWithWrongPassword() {
        BulletinBoard bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO1);

        DemoApplicationTests.loginTestUser(userService, 2);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> bulletinBoardService.putBulletinBoard(
                        bulletinBoard.getId(),
                        bulletinBoardRequestDTO2
                )
        );
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
    }

    @Test
    void deleteBulletinBoard() {
        BulletinBoard bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO1);

        assertDoesNotThrow(
                () -> bulletinBoardService.deleteBulletinBoard(
                        bulletinBoard.getId()
                )
        );

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> bulletinBoardService.deleteBulletinBoard(
                        bulletinBoard.getId()
                )
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}