package com.nhnacademy.taskapi.image.controller;

import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // 이미지 등록
    @PostMapping("/register")
    public ResponseEntity<Image> registerImage(@RequestParam("file") MultipartFile file,
                                               @RequestParam("bookImageName") String bookImageName) throws IOException {

        // 파일을 NHN Cloud Image Manager로 업로드하고 imageUrl을 가져옴
        String imageUrl = imageService.saveImageToCloud(file, bookImageName);

        // 이미지 정보를 데이터베이스에 저장
        Image image = imageService.registerImage(imageUrl, bookImageName);

        return new ResponseEntity<>(image, HttpStatus.CREATED);
    }

    // 이미지 수정
    @PutMapping("/update/{imageId}")
    public ResponseEntity<Image> updateImage(@PathVariable long imageId,
                                             @RequestParam("file") MultipartFile file,
                                             @RequestParam("bookImageName") String bookImageName) throws IOException {

        // 파일을 NHN Cloud Image Manager로 업로드하고 imageUrl을 가져옴
        String imageUrl = imageService.saveImageToCloud(file, bookImageName);

        // 이미지 정보를 수정 후 저장
        Image updatedImage = imageService.updateImage(imageId, imageUrl, bookImageName);

        return new ResponseEntity<>(updatedImage, HttpStatus.OK);
    }

    // 이미지 삭제
    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
