package com.nhnacademy.taskapi.image.service;

import com.nhnacademy.taskapi.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    // 이미지 클라우드에 업로드
    String saveImageToCloud(MultipartFile file, String bookImageName) throws IOException;

    // 이미지 등록 (이미지 URL을 DB에 저장)
    Image registerImage(String imageUrl, String bookImageName);

    // 이미지 수정
    Image updateImage(long imageId, String imageUrl, String bookImageName);

    // 이미지 삭제
    void deleteImage(long imageId);
}
