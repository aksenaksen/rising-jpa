package com.example.demo.controller.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {
    @NotBlank
    @Size(min = 10, max = 20)
    private String content;
    
    @NotNull
    private Integer postId;
    
    @NotNull
    private Integer userId;
}
