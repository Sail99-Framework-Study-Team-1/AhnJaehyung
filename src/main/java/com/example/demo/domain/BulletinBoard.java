package com.example.demo.domain;

import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.BulletinBoardResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public BulletinBoard() {}

    public BulletinBoard(BulletinBoardRequestDTO dto) {
        this.setTitle(dto.getTitle());
        this.setContent(dto.getContent());
        this.setAuthor(dto.getAuthor());
        this.setPassword(dto.getPassword());
    }

    public void hashPassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

    public boolean isWrongPassword(String password, PasswordEncoder encoder) {
        return !encoder.matches(password, this.password);
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
