package com.example.demo.domain;

import com.example.demo.dto.ReplyResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Reply implements UserValidatableEntity {
    @EmbeddedId
    @Column(nullable = false)
    ReplyID id;

    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    User author;

    @Column(nullable = false)
    Date createdAt;

    @Column(nullable = true)
    Date deletedAt;

    @Override
    public Long accessibleUserId() {
        return author.getId();
    }

    public ReplyResponseDTO toResponseDTO() {
        ReplyResponseDTO replyResponseDTO = new ReplyResponseDTO();
        replyResponseDTO.setId(id);
        replyResponseDTO.setContent(content);
        replyResponseDTO.setAuthor(author.toUserResponseDTO());
        replyResponseDTO.setCreatedAt(createdAt);
        return replyResponseDTO;
    }
}
