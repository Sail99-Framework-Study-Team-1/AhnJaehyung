package com.example.demo.domain;

import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.BulletinBoardResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = true)
    private Date deletedAt;

    public BulletinBoard() {}

    public BulletinBoard(BulletinBoardRequestDTO dto) {
        this.setTitle(dto.getTitle());
        this.setContent(dto.getContent());
    }

    public BulletinBoardResponseDTO toResponseDTO() {
        BulletinBoardResponseDTO responseDTO = new BulletinBoardResponseDTO();
        responseDTO.setId(id);
        responseDTO.setTitle(title);
        responseDTO.setContent(content);
        responseDTO.setAuthor(author.toUserResponseDTO());
        responseDTO.setCreatedAt(createdAt);
        return responseDTO;
    }
}
