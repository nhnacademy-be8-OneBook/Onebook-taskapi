package com.nhnacademy.taskapi.coupon.service.policies;

import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForBookRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.request.AddRatePolicyForCategoryRequest;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForBookResponse;
import com.nhnacademy.taskapi.coupon.domain.dto.policies.response.AddRatePolicyForCategoryResponse;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForBook;
import com.nhnacademy.taskapi.coupon.domain.entity.policies.RatePolicyForCategory;
import com.nhnacademy.taskapi.coupon.domain.entity.status.PolicyStatus;
import com.nhnacademy.taskapi.coupon.exception.CategoryNotFoundException;
import com.nhnacademy.taskapi.coupon.exception.PolicyStatusNotFoundException;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForBookRepository;
import com.nhnacademy.taskapi.coupon.repository.policies.RatePoliciesForCategoryRepository;
import com.nhnacademy.taskapi.coupon.repository.status.PolicyStatusRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private RatePoliciesForBookRepository ratePoliciesForBookRepository;
    @Mock
    private RatePoliciesForCategoryRepository ratePoliciesForCategoryRepository;
    @Mock
    private  PolicyStatusRepository policyStatusRepository;
    @InjectMocks
    private PolicyService policyService;

    private Book book;
    private Category category;
    private PolicyStatus policyStatus;
    
    private RatePolicyForBook ratePolicyForBook;
    private RatePolicyForCategory ratePolicyForCategory;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        // 테스트용 출판사
        Publisher publisher = new Publisher();
        publisher.setName("파랑출판사");

        // 테스트용 도서
        book  = new Book();
        for(Field field : book.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(book.getClass().getDeclaredField("bookId"), book,1L );
        ReflectionUtils.setField(book.getClass().getDeclaredField("title"), book,"테스트 title" );
        ReflectionUtils.setField(book.getClass().getDeclaredField("content"), book,"테스트 content" );
        ReflectionUtils.setField(book.getClass().getDeclaredField("description"), book,"테스트 description");
        ReflectionUtils.setField(book.getClass().getDeclaredField("isbn13"), book,"테스트 isbn13");
        ReflectionUtils.setField(book.getClass().getDeclaredField("price"), book,10000);
        ReflectionUtils.setField(book.getClass().getDeclaredField("salePrice"), book,8000);
        ReflectionUtils.setField(book.getClass().getDeclaredField("amount"), book,500);
        ReflectionUtils.setField(book.getClass().getDeclaredField("views"), book,0);
        ReflectionUtils.setField(book.getClass().getDeclaredField("publisher"), book,publisher);
        ReflectionUtils.setField(book.getClass().getDeclaredField("pubdate"),
                book, LocalDate.of(2023,1,1));

        // 테스트용 카테고리
        category = new Category();
        for(Field field : category.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(category.getClass().getDeclaredField("name"),category,"소설" );

        // 테스트용 정책상태
        policyStatus = new PolicyStatus();
        for(Field field :policyStatus.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(policyStatus.getClass().getDeclaredField("policyStatusId"), policyStatus,0);
        ReflectionUtils.setField(policyStatus.getClass().getDeclaredField("name"), policyStatus,"미사용" );

    }

    @Test
    @DisplayName("정률 정책 for Book을 새로 추가 - 정상 동작 테스트")
    void addRatePolicyForBookTest() throws NoSuchFieldException {

        // 정책 추가 요청
        AddRatePolicyForBookRequest addRatePolicyForBookRequest = new AddRatePolicyForBookRequest();
        for(Field field : addRatePolicyForBookRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForBookRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForBookRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForBookRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForBookRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForBookRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("bookId")
                ,addRatePolicyForBookRequest
                ,1L);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForBookRequest
                ,0);

        Mockito.when(bookRepository.findById(addRatePolicyForBookRequest.getBookId()))
                .thenReturn(Optional.of(book));
        Mockito.when(policyStatusRepository.findById(addRatePolicyForBookRequest.getPolicyStatusId()))
                .thenReturn(Optional.of(policyStatus));

        // 예싱 응답
        AddRatePolicyForBookResponse expected =
                new AddRatePolicyForBookResponse(
                        10,
                        30000,
                        10000,
                        LocalDateTime.of(2024,1,1,12,0),
                        LocalDateTime.of(2024,1,10,12,0),
                        "테스트용 정책",
                        "테스트용 정책 설명",
                        1L,
                        0
                );

        // 실제 응답
        AddRatePolicyForBookResponse actual
                = policyService.addRatePolicyForBook(addRatePolicyForBookRequest);

        // addRatePolicyForBook() 메서드 안의 ratePoliciesForBookRepository.save()가 실행되었는지 검증
        Mockito.verify(ratePoliciesForBookRepository,Mockito.times(1))
                .save(Mockito.any(RatePolicyForBook.class));

        // 예상응답과 실제응답이 같은지 검증
        Assertions.assertEquals(
                expected,actual
        );

    }

    @Test
    @DisplayName("정률 정책 for Book을 새로 추가 - 해당하는 ID의 도서가 없을때 - Exception이 정상적으로 발생하는지")
    void addRatePolicyForBookWhenBookNotFoundExceptionTest() throws NoSuchFieldException {


        // 정책 추가 요청
        AddRatePolicyForBookRequest addRatePolicyForBookRequest = new AddRatePolicyForBookRequest();
        for(Field field : addRatePolicyForBookRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForBookRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForBookRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForBookRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForBookRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForBookRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("bookId")
                ,addRatePolicyForBookRequest
                ,99999L); // 존재햐지 않는 도서 ID
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForBookRequest
                ,0);

        Mockito.when(bookRepository.findById(99999L)).thenThrow(BookNotFoundException.class);

        Assertions.assertThrows(BookNotFoundException.class,()->{
                    policyService.addRatePolicyForBook(addRatePolicyForBookRequest);
                }
        );
    }

    @Test
    @DisplayName("정률 정책 for Book을 새로 추가 - 해당하는 ID의 정책상태가 없을때 - Exception이 정상적으로 발생하는지")
    void addRatePolicyForBookWhenPolicyStatusNotFoundExceptionTest() throws NoSuchFieldException {


        // 정책 추가 요청
        AddRatePolicyForBookRequest addRatePolicyForBookRequest = new AddRatePolicyForBookRequest();
        for(Field field : addRatePolicyForBookRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForBookRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForBookRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForBookRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForBookRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForBookRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForBookRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("bookId")
                ,addRatePolicyForBookRequest
                ,1L);
        ReflectionUtils.setField(
                addRatePolicyForBookRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForBookRequest
                ,99999); // 존재하지 않는 정책상태 ID


        Mockito.when(bookRepository.findById(addRatePolicyForBookRequest.getBookId()))
                .thenReturn(Optional.of(book));
        Mockito.when(policyStatusRepository.findById(99999)).thenThrow(
                PolicyStatusNotFoundException.class
        );


        Assertions.assertThrows(PolicyStatusNotFoundException.class,()->{
                    policyService.addRatePolicyForBook(addRatePolicyForBookRequest);
                }
        );
    }

    @Test
    @DisplayName("정률 정책 for Category를 새로 추가 - 정상 동작 테스트")
    void addRatePolicyForCategoryTest() throws NoSuchFieldException {

        // 정책 추가 요청
        AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest = new AddRatePolicyForCategoryRequest();
        for(Field field : addRatePolicyForCategoryRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForCategoryRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForCategoryRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForCategoryRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("categoryId")
                ,addRatePolicyForCategoryRequest
                ,0);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForCategoryRequest
                ,0);

        Mockito.when(categoryRepository.findById(addRatePolicyForCategoryRequest.getCategoryId()))
                .thenReturn(Optional.of(category));
        Mockito.when(policyStatusRepository.findById(addRatePolicyForCategoryRequest.getPolicyStatusId()))
                .thenReturn(Optional.of(policyStatus));

        // 예싱 응답
        AddRatePolicyForCategoryResponse expected =
                new AddRatePolicyForCategoryResponse(
                        10,
                        30000,
                        10000,
                        LocalDateTime.of(2024,1,1,12,0),
                        LocalDateTime.of(2024,1,10,12,0),
                        "테스트용 정책",
                        "테스트용 정책 설명",
                        0,
                        0
                );

        // 실제 응답
        AddRatePolicyForCategoryResponse actual
                = policyService.addRatePolicyForCategory(addRatePolicyForCategoryRequest);

        // addRatePolicyForBook() 메서드 안의 ratePoliciesForBookRepository.save()가 실행되었는지 검증
        Mockito.verify(ratePoliciesForCategoryRepository,Mockito.times(1))
                .save(Mockito.any(RatePolicyForCategory.class));

        // 예상응답과 실제응답이 같은지 검증
        Assertions.assertEquals(
                expected,actual
        );

    }

    @Test
    @DisplayName("정률 정책 for Category를 새로 추가 - 해당하는 ID의 카테고리가 없을때 - Exception이 정상적으로 발생하는지")
    void addRatePolicyForCategoryWhenCategoryNotFoundTest() throws NoSuchFieldException {

        // 정책 추가 요청
        AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest = new AddRatePolicyForCategoryRequest();
        for(Field field : addRatePolicyForCategoryRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForCategoryRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForCategoryRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForCategoryRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("categoryId")
                ,addRatePolicyForCategoryRequest
                ,99999); // 존재하지 않는 카테고리 번호
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForCategoryRequest
                ,0);

        Mockito.when(categoryRepository.findById(99999))
                .thenThrow(CategoryNotFoundException.class);

        Assertions.assertThrows(CategoryNotFoundException.class,
                ()->{
                    policyService.addRatePolicyForCategory(addRatePolicyForCategoryRequest);
                });
    }

    @Test
    @DisplayName("정률 정책 for Category를 새로 추가 - 해당하는 ID의 정책상태가 없을때 - Exception이 정상적으로 발생하는지")
    void addRatePolicyForCategoryWhenPolicyStatusNotFoundException() throws NoSuchFieldException {

        // 정책 추가 요청
        AddRatePolicyForCategoryRequest addRatePolicyForCategoryRequest = new AddRatePolicyForCategoryRequest();
        for(Field field : addRatePolicyForCategoryRequest.getClass().getDeclaredFields()){
            field.setAccessible(true);
        }
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("discountRate")
                ,addRatePolicyForCategoryRequest
                , 10);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("minimumOrderAmount")
                ,addRatePolicyForCategoryRequest
                , 30000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("maximumDiscountPrice")
                ,addRatePolicyForCategoryRequest
                , 10000);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodStart")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,1,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("expirationPeriodEnd")
                ,addRatePolicyForCategoryRequest
                , LocalDateTime.of(2024,1,10,12,0));
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("name")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("description")
                ,addRatePolicyForCategoryRequest
                , "테스트용 정책 설명");
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("categoryId")
                ,addRatePolicyForCategoryRequest
                ,0);
        ReflectionUtils.setField(
                addRatePolicyForCategoryRequest.getClass().getDeclaredField("policyStatusId")
                ,addRatePolicyForCategoryRequest
                ,99999); // 존재하지 않는 정책상태 ID

        Mockito.when(categoryRepository.findById(addRatePolicyForCategoryRequest.getCategoryId()))
                .thenReturn(Optional.of(category));
        Mockito.when(policyStatusRepository.findById(99999)).thenThrow(PolicyStatusNotFoundException.class);

        Assertions.assertThrows(PolicyStatusNotFoundException.class,
                ()->{
                    policyService.addRatePolicyForCategory(addRatePolicyForCategoryRequest);
                });
    }
}