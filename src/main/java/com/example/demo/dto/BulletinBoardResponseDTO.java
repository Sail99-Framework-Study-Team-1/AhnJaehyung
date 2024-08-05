package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BulletinBoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Date createdAt;
}
