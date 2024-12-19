//package com.nhnacademy.taskapi.serviceImplTest;
//import com.nhnacademy.taskapi.adapter.NHNCloudImageManagerAdapter;
//import com.nhnacademy.taskapi.book.domain.Book;
//import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
//import com.nhnacademy.taskapi.book.repository.BookRepository;
//import com.nhnacademy.taskapi.image.domain.Image;
//import com.nhnacademy.taskapi.image.repository.ImageRepository;
//import com.nhnacademy.taskapi.image.service.Impl.ImageServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ImageServiceImplTest {
//
//    @Mock
//    private ImageRepository imageRepository;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private NHNCloudImageManagerAdapter adapter;
//
//    @Mock
//    private MultipartFile multipartFile;
//
//    @InjectMocks
//    private ImageServiceImpl imageService;
//
//    private Book book;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // 테스트용 Book 객체 생성
//        book = new Book();
//        book.setBookId(1L);  // bookId = 1
//        book.setTitle("Test Book");
//        book.setDescription("This is a test book.");
//        book.setIsbn13("978-3-16-148410-0");
//        book.setPrice(10000);
//        book.setSalePrice(8000);
//        book.setAmount(100);
//        book.setViews(0);
//        book.setPubdate(LocalDate.of(2020, 1, 1));
//    }
//
//    @Test
//    void testUploadImage() throws Exception {
//        // Mocking the adapter's uploadImageToImageManager method
//        String mockImageUrl = "http://image.toast.com/aaaacmr/onebook/bookImage/소년이_온다.jpg";
//        when(adapter.uploadImageToImageManager(multipartFile)).thenReturn(mockImageUrl);
//
//        // Mocking the bookRepository to return the mock Book
//        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
//
//        // Mocking the imageRepository to return the image after saving
//        Image savedImage = new Image();
//        savedImage.setBook(book);
//        savedImage.setImageUrl(mockImageUrl);
//        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
//
//        // Calling the method
//        Image image = imageService.uploadImage(1L, multipartFile);
//
//        // Verifying the interactions
//        verify(adapter, times(1)).uploadImageToImageManager(multipartFile);
//        verify(bookRepository, times(1)).findById(1L);
//        verify(imageRepository, times(1)).save(any(Image.class));
//
//        // Asserting the result
//        assertNotNull(image);
//        assertEquals(mockImageUrl, image.getImageUrl());
//        assertEquals(book, image.getBook());
//    }
//
//    @Test
//    void testUploadImage_BookNotFound() throws Exception {
//        // Mocking the bookRepository to return an empty Optional for the bookId
//        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//
//        // Calling the method and verifying that it throws a BookNotFoundException
//        Exception exception = assertThrows(BookNotFoundException.class, () -> {
//            imageService.uploadImage(1L, multipartFile);
//        });
//
//        // Verifying the exception message
//        assertEquals("Book with id 1 not found", exception.getMessage());
//    }
//}
