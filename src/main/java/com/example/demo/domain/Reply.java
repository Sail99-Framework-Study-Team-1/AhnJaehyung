package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public Long accessibleUserId() {
        return author.getId();
    }
}
