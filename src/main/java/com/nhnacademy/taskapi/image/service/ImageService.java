package com.nhnacademy.taskapi.image.service;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.dto.ImageSaveDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    // 이미지 클라우드에 업로드
    Image saveImage (ImageSaveDTO dto);
    //로컬 저장
    Image saveImage(MultipartFile file, Book book) throws IOException;
    void deleteImage(long imageId);
    Image getImage(long bookId);
}