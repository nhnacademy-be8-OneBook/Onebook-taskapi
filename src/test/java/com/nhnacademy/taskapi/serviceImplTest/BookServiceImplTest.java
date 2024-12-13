package com.nhnacademy.taskapi.serviceImplTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.book.service.Impl.BookServiceImpl;
import com.nhnacademy.taskapi.dto.BookAladinDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JsonNode rootNode;

    @Mock
    private JsonNode itemNode;

    @Mock
    private JsonNode bookItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAladin() throws Exception {
        // JSON 응답 시뮬레이션
        String jsonResponse = "{"
                + "\"item\": ["
                + "{"
                + "\"title\": \"Book Title\","
                + "\"author\": \"Author Name\","
                + "\"pubDate\": \"2023-12-12\","
                + "\"description\": \"Book Description\","
                + "\"isbn13\": \"1234567890123\","
                + "\"priceSales\": 15000,"
                + "\"priceStandard\": 20000,"
                + "\"categoryName\": \"Category Name\","
                + "\"publisher\": \"Publisher Name\","
                + "\"salesPoint\": 1000"
                + "}]"
                + "}";

        // Mocking RestTemplate의 동작
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse);

        // Mocking ObjectMapper의 동작
        when(objectMapper.readTree(jsonResponse)).thenReturn(rootNode);
        when(rootNode.path("item")).thenReturn(itemNode);

        // Mocking itemNode의 동작
        JsonNode bookItem = mock(JsonNode.class);
        when(itemNode.iterator()).thenReturn(Collections.singletonList(bookItem).iterator());


        // 서비스 메소드 호출
        List<BookAladinDTO> result = bookService.saveAladin();

        // 검증
        assertNotNull(result);
        assertEquals(7, result.size());  // 리스트에 하나의 책 정보가 있어야 합니다.
    }
}
