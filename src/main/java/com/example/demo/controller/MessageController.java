package com.example.demo.controller;

import com.example.demo.controller.dto.RequestCreateMessage;
import com.example.demo.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<Void> createMessage(@RequestBody RequestCreateMessage request){

        messageService.createMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
