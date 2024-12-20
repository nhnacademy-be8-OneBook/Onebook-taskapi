package com.nhnacademy.taskapi.book.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.service.TagService;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.service.AuthorService;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.dto.BookAuthorCreateDTO;
import com.nhnacademy.taskapi.book.dto.BookCategorySaveDTO;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.exception.*;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookAuthorService;
import com.nhnacademy.taskapi.book.service.BookCategoryService;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.book.service.BookTagService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.dto.CategoryCreateDTO;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.category.service.CategoryService;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.service.PublisherService;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.dto.StockCreateUpdateDTO;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
import com.nhnacademy.taskapi.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookCategoryService bookCategoryService;
    private final BookAuthorService bookAuthorService;
    private final CategoryService categoryService;
    private final StockService stockService;
    private final TagService tagService;
    private final BookTagService bookTagService;
    private final AladinApiAdapter aladinApiAdapter;

    // 알라딘 API 데이터 파싱
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BookSaveDTO> saveAladin() {
        List<BookSaveDTO> dtoList = new ArrayList<>();
        String url = "https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtjswns12211534001&QueryType=Bestseller&MaxResults=50&start=1&SearchTarget=Book&output=js&Version=20131101";
        String response = aladinApiAdapter.fetchAladinData(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemNode = rootNode.path("item");

            for (JsonNode item : itemNode) {
                BookSaveDTO dto = new BookSaveDTO();
                dto.setTitle(item.path("title").asText());
                dto.setAuthorName(item.path("author").asText());
                dto.setPubdate(item.path("pubDate").asText());
                dto.setDescription(item.path("description").asText());
                dto.setIsbn13(item.path("isbn13").asText());
                dto.setPriceSales(item.path("priceSales").asInt());
                dto.setPrice(item.path("priceStandard").asInt());
                dto.setCategoryName(item.path("categoryName").asText());
                dto.setPublisherName(item.path("publisher").asText());
                dto.setSalesPoint(item.path("salesPoint").asLong());
                dtoList.add(dto);
            }
            return dtoList;

        } catch (JsonMappingException e) {
            // JsonMappingException 처리
            throw new RuntimeException("Error mapping JSON", e);
        } catch (JsonProcessingException e) {
            // JsonProcessingException 처리
            throw new RuntimeException("Error processing JSON", e);
        }
    }

    // 알라딘 API로부터 책 정보를 받아서 저장
    @Override
    @Transactional
    public void saveBookFromAladin() {
        List<BookSaveDTO> dtoList = saveAladin();
        for (BookSaveDTO dto : dtoList) {
            saveBook(dto);
        }
    }


    @Override
    @Transactional
    public Book updateBook(Long bookId, BookUpdateDTO bookUpdateDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found!"));

        // 책 정보 수정
        book.setTitle(bookUpdateDTO.getTitle());
        book.setContent(bookUpdateDTO.getContent());
        book.setDescription(bookUpdateDTO.getDescription());
        book.setPrice(bookUpdateDTO.getPrice());
        book.setSalePrice(bookUpdateDTO.getSalePrice());

        // 책 재고 수정
        StockCreateUpdateDTO dto = new StockCreateUpdateDTO();
        dto.setBookId(book.getBookId());
        dto.setAmount(bookUpdateDTO.getStock());
        Stock stock = stockService.updateStock(dto);


        return bookRepository.save(book);


    }

    // 책 등록 및 수정 (등록/수정 통합 메서드)
    @Transactional
    @Override
    public Book saveBook(BookSaveDTO bookSaveDTO) {
        // 기존 책 조회
        Book book = null;
        if(Objects.nonNull(bookRepository.findByIsbn13(bookSaveDTO.getIsbn13()))) {
            throw new BookDuplicateException("Book already exists !");
        }else{
            book = new Book();
        }

        // 출판사 등록
        Publisher publisher =publisherService.addPublisher(bookSaveDTO.getPublisherName());
        book.setPublisher(publisher);


        // 카테고리 등록
        Category parentCategory = null;
        if(bookSaveDTO.getCategoryName().contains("<")){
            parentCategory = categoryService.addCategoryByAladin(bookSaveDTO.getCategory().getName());
        }else{
            CategoryCreateDTO dto = new CategoryCreateDTO();
            dto.setCategoryName(bookSaveDTO.getCategory().getName());
            dto.setCategory(bookSaveDTO.getCategory());
            parentCategory = categoryService.addCategory(dto);
        }

        // 작가 등록
        Author author = authorService.addAuthor(bookSaveDTO.getAuthorName());


        // 태그 등록 - 태그가 Null이 아닐때만 등록
        Tag tag = null;
        if(Objects.nonNull(bookSaveDTO.getTagName())){
             tag = tagService.addTag(bookSaveDTO.getTagName());
        }


        // 책 등록
        if(bookSaveDTO.getIsbn13().length() != 13){
            throw new InvalidIsbnException("The provided ISBN-13 format is incorrect !");
        }
        // 판매량 없으면 기본값 0설정
        if(Objects.isNull(bookSaveDTO.getSalesPoint())){
            book.setAmount(0);
        }else{
            book.setAmount(bookSaveDTO.getSalesPoint());
        }

        book.setTitle(bookSaveDTO.getTitle());
        book.setDescription(bookSaveDTO.getDescription());
        //ISBN-13 유효성 검사
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getPriceSales());
        book.setViews(0);
        // 출판일 설정
        if (bookSaveDTO.getPubdate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            book.setPubdate(LocalDate.parse(bookSaveDTO.getPubdate(), formatter));
        }
        book = bookRepository.save(book);

        // 책-작가 연결
        BookAuthorCreateDTO bookAuthorCreateDTO = new BookAuthorCreateDTO();
        bookAuthorCreateDTO.setBookId(book.getBookId());
        bookAuthorCreateDTO.setAuthorId(author.getAuthorId());
        BookAuthor bookAuthor = bookAuthorService.createBookAuthor(bookAuthorCreateDTO);

        // 책-태그 연결
        if(Objects.nonNull(tag)){
            bookTagService.addBookTag(book.getBookId(), tag.getTagId());
        }

        //책 - 카테고리 등록
        BookCategory bookCategory = null;
        BookCategorySaveDTO bookCategorySaveDTO = new BookCategorySaveDTO();
        bookCategorySaveDTO.setBookId(book.getBookId());
        bookCategorySaveDTO.setCategoryId(parentCategory.getCategoryId());
        bookCategoryService.save(bookCategorySaveDTO);

        // 재고 등록
        StockCreateUpdateDTO stockCreateUpdateDTO = new StockCreateUpdateDTO();
        stockCreateUpdateDTO.setBookId(book.getBookId());
        stockCreateUpdateDTO.setAmount(100);
        stockService.addStock(stockCreateUpdateDTO);

        return book;
    }

    // 책 삭제
    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        // 책 조회
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));

        // 책 삭제
        bookRepository.delete(book);
    }

    // 베스트셀러 목록 조회
    @Override
    public Page<Book> bestSellerBooks(Pageable pageable) {
        return bookRepository.findAllByOrderByAmount(pageable);
    }


    @Override
    public Page<Book> newBooks(Pageable pageable) {
        return bookRepository.findAllByOrderByPubdate(pageable);
    }




    //책 조회
    @Override
    public Book getBook(long bookId){
        if(!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Book not exist !");
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return book;
    }


}
