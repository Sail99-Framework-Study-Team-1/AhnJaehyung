package com.example.demo.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;

@Embeddable
public class ReplyID implements Serializable {
    private Long bulletinBoardId;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long replyId;
}