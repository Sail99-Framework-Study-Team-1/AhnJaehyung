package com.example.demo.domain;

import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.BulletinBoardResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class BulletinBoard {

    @Id
    @GeneratedValue()
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = true)
    private Date deletedAt;



    public static BulletinBoard fromRequestDTO(BulletinBoardRequestDTO dto) {
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setTitle(dto.getTitle());
        bulletinBoard.setContent(dto.getContent());
        bulletinBoard.setAuthor(dto.getAuthor());
        bulletinBoard.setPassword(dto.getPassword());
        return bulletinBoard;
    }

    public BulletinBoardResponseDTO toResponseDTO() {
        BulletinBoardResponseDTO responseDTO = new BulletinBoardResponseDTO();
        responseDTO.setId(id);
        responseDTO.setTitle(title);
        responseDTO.setContent(content);
        responseDTO.setAuthor(author);
        responseDTO.setCreatedAt(createdAt);
        return responseDTO;
    }
}
