package com.nhnacademy.taskapi.image.controller;

import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.dto.ImageSaveDTO;
import com.nhnacademy.taskapi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<Image> uploadImage(@RequestParam("image") MultipartFile imageFile,
                                             @RequestParam("bookId") long bookId,
                                             @RequestParam("imageName") String imageName) {
        if(imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }


        try {
            ImageSaveDTO imageSaveDTO = new ImageSaveDTO();
            imageSaveDTO.setBookId(bookId);
            imageSaveDTO.setImageName(imageName);
            imageSaveDTO.setImageBytes(imageFile.getBytes());

            Image image = imageService.saveImage(imageSaveDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(image);
        } catch (IOException e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable("imageId") long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<Image> getImage(@PathVariable("bookId") long bookId) {
        Image image = imageService.getImage(bookId);
        return ResponseEntity.ok().body(image);
    }




}