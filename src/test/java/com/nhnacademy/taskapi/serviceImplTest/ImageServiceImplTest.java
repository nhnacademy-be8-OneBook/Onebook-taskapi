package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.adapter.NHNCloudImageManagerAdapter;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.image.service.Impl.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private NHNCloudImageManagerAdapter adapter;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImageServiceImpl imageService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 테스트용 Book 객체 생성
        book = new Book();
        book.setBookId(1L);  // bookId = 1
        book.setTitle("Test Book");
        book.setDescription("This is a test book.");
        book.setIsbn13("978-3-16-148410-0");
        book.setPrice(10000);
        book.setSalePrice(8000);
        book.setAmount(100);
        book.setViews(0);
        book.setPubdate(LocalDate.of(2020, 1, 1));
    }

    // 정상적인 이미지 업로드 테스트
    @Test
    void testUploadImage() throws Exception {
        // Mocking the adapter's uploadImageToImageManager method
        String mockImageUrl = "http://image.toast.com/aaaacmr/onebook/bookImage/소년이_온다.jpg";
        when(adapter.uploadImageToImageManager(multipartFile)).thenReturn(mockImageUrl);

        // Mocking the bookRepository to return the mock Book
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // Mocking the imageRepository to return the image after saving
        Image savedImage = new Image();
        savedImage.setBook(book);
        savedImage.setImageUrl(mockImageUrl);
        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);

        // Calling the method
        Image image = imageService.uploadImage(1L, multipartFile);

        // Verifying the interactions
        verify(adapter, times(1)).uploadImageToImageManager(multipartFile);
        verify(bookRepository, times(1)).findById(1L);
        verify(imageRepository, times(1)).save(any(Image.class));

        // Asserting the result
        assertNotNull(image);
        assertEquals(mockImageUrl, image.getImageUrl());
        assertEquals(book, image.getBook());
    }

    // BookNotFoundException이 발생하는 경우
    @Test
    void testUploadImage_BookNotFound() throws Exception {
        // Mocking the bookRepository to return an empty Optional for the bookId
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Calling the method and verifying that it throws a BookNotFoundException
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            imageService.uploadImage(1L, multipartFile);
        });

        // Verifying the exception message
        assertEquals("Book with id 1 not found", exception.getMessage());
    }

    // 클라우드 서비스에서 업로드 실패 시 예외 발생
    @Test
    void testUploadImage_CloudServiceError() throws Exception {
        // 클라우드 서비스에서 업로드 실패 시 예외 던지기
        when(adapter.uploadImageToImageManager(multipartFile)).thenThrow(new RuntimeException("Cloud service error"));

        // 책 정보는 정상적으로 조회된다고 가정
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // 예외 처리 확인
        Exception exception = assertThrows(RuntimeException.class, () -> {
            imageService.uploadImage(1L, multipartFile);
        });

        assertEquals("Cloud service error", exception.getMessage());
    }

    // MultipartFile이 비어 있는 경우
    @Test
    void testUploadImage_EmptyFile() throws Exception {
        // 빈 MultipartFile 객체
        MultipartFile emptyFile = mock(MultipartFile.class);
        when(emptyFile.isEmpty()).thenReturn(true);

        // 책 정보는 정상적으로 조회된다고 가정
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // 업로드 실패 (빈 파일에 대한 예외)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.uploadImage(1L, emptyFile);
        });

        assertEquals("Uploaded file is empty", exception.getMessage());
    }

    // 이미지 저장 실패 (이미지 리포지토리에서 예외 발생)
    @Test
    void testUploadImage_ImageSaveFailure() throws Exception {
        // 클라우드 서비스 업로드 성공 시 URL 반환
        String mockImageUrl = "http://image.toast.com/aaaacmr/onebook/bookImage/소년이_온다.jpg";
        when(adapter.uploadImageToImageManager(multipartFile)).thenReturn(mockImageUrl);

        // 책 정보는 정상적으로 조회된다고 가정
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // 이미지 저장 실패
        when(imageRepository.save(any(Image.class))).thenThrow(new RuntimeException("Failed to save image"));

        // 예외 발생 확인
        Exception exception = assertThrows(RuntimeException.class, () -> {
            imageService.uploadImage(1L, multipartFile);
        });

        assertEquals("Failed to save image", exception.getMessage());
    }

    // 파일이 null인 경우 처리
    @Test
    void testUploadImage_NullFile() throws Exception {
        // null인 파일을 전달한 경우
        MultipartFile nullFile = null;

        // 책 정보는 정상적으로 조회된다고 가정
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // 예외 발생 확인
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.uploadImage(1L, nullFile);
        });

        assertEquals("Uploaded file is null", exception.getMessage());
    }

    // 정상적인 업로드 후 이미지 URL이 제대로 설정되는지 확인
    @Test
    void testUploadImage_SuccessfulUpload() throws Exception {
        // 클라우드 업로드 성공
        String mockImageUrl = "http://image.toast.com/aaaacmr/onebook/bookImage/소년이_온다.jpg";
        when(adapter.uploadImageToImageManager(multipartFile)).thenReturn(mockImageUrl);

        // 책 정보는 정상적으로 조회
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        // 이미지 저장
        Image savedImage = new Image();
        savedImage.setBook(book);
        savedImage.setImageUrl(mockImageUrl);
        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);

        // 메서드 호출
        Image image = imageService.uploadImage(1L, multipartFile);

        // 결과 검증
        assertNotNull(image);
        assertEquals(mockImageUrl, image.getImageUrl());
        assertEquals(book, image.getBook());
    }
}