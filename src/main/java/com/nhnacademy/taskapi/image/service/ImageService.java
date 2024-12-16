package com.nhnacademy.taskapi.image.service;

import com.nhnacademy.taskapi.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService  {
    Image uploadImage(long bookId, MultipartFile file);
}
