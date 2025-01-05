package com.nhnacademy.taskapi.keyManager.controller;

import com.nhnacademy.taskapi.keyManager.dto.KeyResponseDto;
import com.nhnacademy.taskapi.keyManager.service.KeyFactoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KeyFactoryController {

    private final KeyFactoryManager keyFactoryManager;

    @GetMapping("/keyFactory")
    public KeyResponseDto keyFactory() {
        ResponseEntity<KeyResponseDto> keyResponseDtoResponseEntity = keyFactoryManager.keyInit();
        KeyResponseDto body = keyResponseDtoResponseEntity.getBody();

        return body;
    }
}
