package com.nhnacademy.taskapi.book.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    // 신간 리스트 50개 정보 받아오기
    @Override
    @Transactional
    public void saveBookFromAladin() {
        List<Book> bookList = new ArrayList<>();
        String title;
        String author;
        String pubdate;
        String description;
        String isbn13;
        String priceSales;
        String price;
        String categoryName;
        String publisher;


        String url = "https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtjswns12211534001&QueryType=ItemNewAll&MaxResults=50&start=1&SearchTarget=Book&output=js&Version=20131101";
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemNode = rootNode.path("item");

            for(JsonNode item : itemNode) {
                item.path("title").asText();
                
            }




        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book saveBook(BookSaveDTO bookSaveDTO) {
        Book book = new Book();

        book.setTitle(bookSaveDTO.getTitle());
        book.setContent(bookSaveDTO.getContent());
        book.setDescription(bookSaveDTO.getDescription());
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getSalePrice());
        book.setAmount(bookSaveDTO.getAmount());
        book.setPubdate(bookSaveDTO.getPubDate());

        return bookRepository.save(book);
    }
}
