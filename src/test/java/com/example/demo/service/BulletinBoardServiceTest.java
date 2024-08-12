package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BulletinBoardServiceTest {

    @Autowired
    private BulletinBoardService bulletinBoardService;

    @Test
    void putBulletinBoard() throws Exception {
        BulletinBoardRequestDTO originalBulletinBoardRequestDTO = new BulletinBoardRequestDTO();
        originalBulletinBoardRequestDTO.setTitle("Title");
        originalBulletinBoardRequestDTO.setContent("Content");
        originalBulletinBoardRequestDTO.setAuthor("me");
        originalBulletinBoardRequestDTO.setPassword("pw1");

        BulletinBoardRequestDTO newBulletinBoardRequestDTO = new BulletinBoardRequestDTO();
        newBulletinBoardRequestDTO.setTitle("Title2");
        newBulletinBoardRequestDTO.setContent("Content2");
        newBulletinBoardRequestDTO.setAuthor("me");
        newBulletinBoardRequestDTO.setPassword("pw1");

        BulletinBoard originalBulletinBoard = bulletinBoardService.postBulletinBoard(originalBulletinBoardRequestDTO);
        BulletinBoard newBulletinBoard = bulletinBoardService.putBulletinBoard(originalBulletinBoard.getId(), newBulletinBoardRequestDTO);
        assertNotEquals(originalBulletinBoard.getTitle(), newBulletinBoard.getTitle());
        assertNotEquals(originalBulletinBoard.getContent(), newBulletinBoard.getContent());
    }

    @Test
    void putBulletinBoardWithWrongPassword() {
        BulletinBoardRequestDTO bulletinBoardRequestDTO = new BulletinBoardRequestDTO();
        bulletinBoardRequestDTO.setTitle("Title");
        bulletinBoardRequestDTO.setContent("Content");
        bulletinBoardRequestDTO.setAuthor("me");
        bulletinBoardRequestDTO.setPassword("pw1");

        BulletinBoard bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO);
        bulletinBoardRequestDTO.setPassword("pw2");
        assertThrows(Exception.class, ()->bulletinBoardService.putBulletinBoard(bulletinBoard.getId(), bulletinBoardRequestDTO));
    }
}