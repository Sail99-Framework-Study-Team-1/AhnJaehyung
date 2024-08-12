package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BulletinBoardServiceTest {

    private final BulletinBoardRequestDTO bulletinBoardRequestDTO1 = new BulletinBoardRequestDTO();
    private final BulletinBoardRequestDTO bulletinBoardRequestDTO2 = new BulletinBoardRequestDTO();

    @Autowired
    private BulletinBoardService bulletinBoardService;

    @BeforeEach
    void setUp() {
        this.bulletinBoardRequestDTO1.setTitle("Title1");
        this.bulletinBoardRequestDTO1.setContent("Content1");
        this.bulletinBoardRequestDTO1.setAuthor("me1");
        this.bulletinBoardRequestDTO1.setPassword("pw1");

        this.bulletinBoardRequestDTO2.setTitle("Title2");
        this.bulletinBoardRequestDTO2.setContent("Content2");
        this.bulletinBoardRequestDTO2.setAuthor("me2");
        this.bulletinBoardRequestDTO2.setPassword("pw2");
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

        assertThrows(
                Exception.class,
                () -> bulletinBoardService.putBulletinBoard(
                        bulletinBoard.getId(),
                        bulletinBoardRequestDTO2
                )
        );
    }

    @Test
    void deleteBulletinBoard() {
        BulletinBoard bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO1);

        assertDoesNotThrow(
                () -> bulletinBoardService.deleteBulletinBoard(
                        bulletinBoard.getId(),
                        bulletinBoardRequestDTO1.getPassword()
                )
        );

        assertThrows(
                Exception.class,
                () -> bulletinBoardService.deleteBulletinBoard(
                        bulletinBoard.getId(),
                        bulletinBoardRequestDTO2.getPassword()
                )
        );
    }
}