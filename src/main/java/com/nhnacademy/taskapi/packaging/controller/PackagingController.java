package com.nhnacademy.taskapi.packaging.controller;

import com.nhnacademy.taskapi.packaging.dto.PackagingRequestDto;
import com.nhnacademy.taskapi.packaging.dto.PackagingResponseDto;
import com.nhnacademy.taskapi.packaging.service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PackagingController {

    private final PackagingService packagingService;

    // TODO ResponseEntity 등록
    @GetMapping("/task/packagings")
    public List<PackagingResponseDto> packaging() {
        List<PackagingResponseDto> allPackaging = packagingService.getAllPackaging();
        return allPackaging;
    }

//    @PostMapping("/task/admin/packaging")
//    public ResponseEntity<Void> registerPackaging(@RequestBody PackagingRequestDto packagingRequestDto) {
//        System.out.println(packagingRequestDto);
//        packagingService.createPackaging(packagingRequestDto);
//        return ResponseEntity.created(null).build();
//    }

    @PostMapping("/task/admin/packaging")
    public void registerPackaging(@RequestBody PackagingRequestDto packagingRequestDto) {
        System.out.println(packagingRequestDto);
        packagingService.createPackaging(packagingRequestDto);
//        return ResponseEntity.created(null).build();
    }

}
