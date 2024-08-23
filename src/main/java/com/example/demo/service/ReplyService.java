package com.example.demo.service;

import com.example.demo.domain.Reply;
import com.example.demo.domain.ReplyID;
import com.example.demo.domain.User;
import com.example.demo.dto.ReplyRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReplyService {
    @Autowired ReplyRepository replyRepository;
    @Autowired BulletinBoardRepository bulletinBoardRepository;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    public Reply postReply(Long bulletinBoardId, ReplyRequestDTO replyRequestDTO) {
        if (!bulletinBoardRepository.existsById(bulletinBoardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Long userId = userService.getAuthenticatedUserIdElseThrow();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        );

        ReplyID replyID = new ReplyID();
        replyID.setBulletinBoardId(bulletinBoardId);

        Reply reply = new Reply();
        reply.setId(replyID);
        reply.setContent(replyRequestDTO.getContent());
        reply.setAuthor(user);
        reply.setCreatedAt(new Date());

        return replyRepository.save(reply);
    }

    public List<Reply> getReplyList(Long bulletinBoardId) {
        return replyRepository.findAllByDeletedAtIsNotNullAndBulletinBoardIDEquals(bulletinBoardId);
    }
}
