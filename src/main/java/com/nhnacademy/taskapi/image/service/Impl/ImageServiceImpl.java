package com.nhnacademy.taskapi.image.service.Impl;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.adapter.NHNCloudImageManagerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageServiceImpl {

    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;
    private final NHNCloudImageManagerAdapter adapter;

    // 생성자 주입
    public ImageServiceImpl(BookRepository bookRepository, ImageRepository imageRepository, NHNCloudImageManagerAdapter adapter) {
        this.bookRepository = bookRepository;
        this.imageRepository = imageRepository;
        this.adapter = adapter;
    }

    /**
     * 이미지 업로드
     * @param bookId - 책의 ID
     * @param file - 업로드할 이미지 파일
     * @return 업로드된 이미지 정보
     * @throws Exception - 예외 처리 (파일 업로드 실패 등)
     */
    public Image uploadImage(Long bookId, MultipartFile file) throws Exception {
        // 파일이 null이거나 비어있는지 확인
        if (file == null) {
            throw new IllegalArgumentException("Uploaded file is null");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        // 책을 찾을 수 없는 경우
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));

        // 클라우드 서비스에 이미지 업로드
        String imageUrl = adapter.uploadImageToImageManager(file);

        // 이미지 객체 생성 및 저장
        Image image = new Image();
        image.setBook(book);
        image.setImageUrl(imageUrl);

        return imageRepository.save(image);
    }
}