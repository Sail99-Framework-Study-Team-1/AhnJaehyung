package com.example.demo.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ReplyID implements Serializable {
    private Long bulletinBoardId;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long replyId;
}