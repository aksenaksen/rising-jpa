package com.example.demo.controller.dto;

import com.example.demo.controller.user.dto.UserResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class PostResponseDto {
    private Long id;
    private String name;
    private String content;
    private List<CommentResponseDto> comments;
    private String createdAt;
    private UserResponseDto createdBy;
    private String updatedAt;
    private UserResponseDto updatedBy;
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}