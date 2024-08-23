package com.example.demo.controller;

import com.example.demo.domain.Reply;
import com.example.demo.dto.ReplyRequestDTO;
import com.example.demo.dto.ReplyResponseDTO;
import com.example.demo.service.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bulletinBoard/{bulletinBoardId}/reply")
public class ReplyController {

    @Autowired ReplyService replyService;

    @PostMapping(value = "")
    ReplyResponseDTO createReply(
            @PathVariable Long bulletinBoardId,
            @Valid @RequestBody ReplyRequestDTO replyRequestDTO
    ) {
        Reply reply = replyService.postReply(bulletinBoardId, replyRequestDTO);
        return reply.toResponseDTO();
//        return new ReplyResponseDTO();
    }
}
