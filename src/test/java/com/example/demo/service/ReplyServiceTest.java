package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.domain.BulletinBoard;
import com.example.demo.domain.Reply;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.ReplyRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyServiceTest {
    @Autowired BulletinBoardService bulletinBoardService;
    @Autowired ReplyService replyService;
    @Autowired UserService userService;

    BulletinBoard bulletinBoard;
    ReplyRequestDTO replyRequestDTO1;
    ReplyRequestDTO replyRequestDTO2;

    @BeforeEach
    void setUp() {
        DemoApplicationTests.loginTestUser(userService, 1);

        BulletinBoardRequestDTO bulletinBoardRequestDTO = new BulletinBoardRequestDTO();
        bulletinBoardRequestDTO.setTitle("Title");
        bulletinBoardRequestDTO.setContent("Content");
        bulletinBoard = bulletinBoardService.postBulletinBoard(bulletinBoardRequestDTO);

        replyRequestDTO1 = new ReplyRequestDTO();
        replyRequestDTO1.setContent("content");

        replyRequestDTO2 = new ReplyRequestDTO();
        replyRequestDTO2.setContent("content fixed");
    }

    @Test
    void saveNormalReplyAndCheck() {
        Reply reply = replyService.postReply(bulletinBoard.getId(), replyRequestDTO1);
        assertEquals(reply.getId().getBulletinBoardId(), bulletinBoard.getId());

        List<Reply> replyList = replyService.getReplyList(bulletinBoard.getId());
        assertEquals(reply.getContent(), replyList.getFirst().getContent());
    }

    @Test
    void saveNoExistBulletinBoardReply() {
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> {
                    replyService.postReply(999L, replyRequestDTO1);
                }
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void saveWithoutAuthentication() {
        DemoApplicationTests.logoutTestUser();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> {
                    replyService.postReply(bulletinBoard.getId(), replyRequestDTO1);
                }
        );
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }
}