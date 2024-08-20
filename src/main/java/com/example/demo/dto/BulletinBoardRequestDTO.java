package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BulletinBoardRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
