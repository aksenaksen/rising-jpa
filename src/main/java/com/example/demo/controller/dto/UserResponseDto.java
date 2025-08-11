package com.example.demo.controller.dto;

import com.example.demo.repository.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {
    private Integer id;
    private String username;
    private String name;
    private String job;
    private String specialty;
    private TeamResponseDto team;
    private List<MessageResponseDto> message;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getName(),
                entity.getJob(),
                entity.getSpecialty(),
                new TeamResponseDto(entity.getTeam().getId(), entity.getTeam().getName()),
                entity.getMessages().stream()
                        .map(message -> new MessageResponseDto(message.getId(), message.getContent()))
                        .toList()
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TeamResponseDto {
        private Long teamId;
        private String teamName;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MessageResponseDto{
        private Long messageId;
        private String message;
    }
}