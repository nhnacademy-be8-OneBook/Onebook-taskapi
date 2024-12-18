package com.nhnacademy.taskapi.image.service.Impl;


import com.nhnacademy.taskapi.adapter.NHNCloudImageManagerAdapter;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final BookRepository bookRepository;
    private static final String API_URL = "https://api-image.nhncloudservice.com";
    private static final String API_KEY = "bgi6iWmF4yYnwQoo";

    private final NHNCloudImageManagerAdapter adapter;


    public Image uploadImage(long bookId, MultipartFile file)  {
        String imageUrl = adapter.uploadImageToImageManager(file);

        Image image = new Image();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));

        image.setBook(book);
        image.setImageUrl(imageUrl);
        return imageRepository.save(image);
    }


}
