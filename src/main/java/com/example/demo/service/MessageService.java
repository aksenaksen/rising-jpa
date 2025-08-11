package com.example.demo.service;

import com.example.demo.controller.dto.RequestCreateMessage;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Message;
import com.example.demo.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createMessage(RequestCreateMessage request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저를 찾을 수 없습니다."));
        Message message = Message.createMessage(request.message(), user);
        messageRepository.save(message);
    }

}
