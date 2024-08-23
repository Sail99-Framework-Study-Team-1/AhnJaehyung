package com.example.demo.dto;

import com.example.demo.domain.ReplyID;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ReplyResponseDTO {
    private ReplyID id;
    private String content;
    private UserResponseDTO author;
    private Date createdAt;
}
