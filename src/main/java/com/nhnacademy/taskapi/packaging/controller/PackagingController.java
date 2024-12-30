package com.nhnacademy.taskapi.packaging.controller;

import com.nhnacademy.taskapi.packaging.dto.PackagingResponseDTO;
import com.nhnacademy.taskapi.packaging.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PackagingController {

    private final PackagingService packagingService;

    @GetMapping("/task/packagings")
    public List<PackagingResponseDTO> packaging() {
        List<PackagingResponseDTO> allPackaging = packagingService.getAllPackaging();
        return allPackaging;
    }
}
