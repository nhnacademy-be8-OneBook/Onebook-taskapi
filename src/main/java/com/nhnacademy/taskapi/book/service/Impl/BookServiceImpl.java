package com.nhnacademy.taskapi.book.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.exception.AuthorNotFoundException;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;


    // 신간 리스트 50개 정보 받아오기
    @Override
    @Transactional
    public void saveBookFromAladin() {
        List<Book> bookList = new ArrayList<>();
        String title;
        String authorName;
        String stringPubdate;
        String description;
        String isbn13;
        int priceSales;
        int price;
        String categoryNames;
        String publisherName;
        long salesPoint;


        String url = "https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtjswns12211534001&QueryType=ItemNewAll&MaxResults=50&start=1&SearchTarget=Book&output=js&Version=20131101";
        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemNode = rootNode.path("item");

            for(JsonNode item : itemNode) {
                title =item.path("title").asText();
                authorName = item.path("author").asText();
                stringPubdate = item.path("pubDate").asText();
                description = item.path("description").asText();
                isbn13 = item.path("isbn13").asText();
                priceSales = item.path("priceSales").asInt();
                price = item.path("priceStandard").asInt();
                categoryNames = item.path("categoryName").asText();
                publisherName = item.path("publisher").asText();
                salesPoint = item.path("salesPoint").asLong();

                //출판사 등록
                Publisher publisher = null;
                if(Objects.isNull(publisherRepository.findByName(publisherName))) {
                    publisher = new Publisher();
                    publisher.setName(publisherName);
                    publisherRepository.save(publisher);
                }else{
                    publisher = publisherRepository.findByName(publisherName);
                }


                //책 등록
                Book book = null;
                if(Objects.isNull(bookRepository.findByIsbn13(isbn13))){
                    book = new Book();
                    book.setPublisher(publisher);
                    book.setTitle(title);
                    book.setDescription(description);
                    book.setIsbn13(isbn13);
                    book.setPrice(price);
                    book.setSalePrice(priceSales);
                    book.setAmount(salesPoint);
                    //조회수 초기값 0 설정
                    book.setViews(0);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                    LocalDate pubdate = LocalDate.parse(stringPubdate, formatter);
                    book.setPubdate(pubdate);

                    bookRepository.save(book);

                }else{
                    book = bookRepository.findByIsbn13(isbn13);
                }


                //작가 등록
                Author author = null;
                if(Objects.isNull(authorRepository.findbyName(authorName))) {
                    author = new Author();
                    author.setName(authorName);
                    authorRepository.save(author);
                }else{
                    author = authorRepository.findbyName(authorName);
                }


                //책-작가 등록
                BookAuthor bookAuthor = null;
                if(Objects.isNull(bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(book.getBookId(), author.getAuthorId()))){
                    bookAuthor.setAuthor(author);
                    bookAuthor.setBook(book);
                    bookAuthorRepository.save(bookAuthor);
                }

                //재고 등록
                if(Objects.isNull(stockRepository.findByBook_bookId(book.getBookId()))) {
                    Stock stock = new Stock();
                    stock.setBook(book);
                    //재고 기본 100 설정
                    stock.setStock(100);
                    stockRepository.save(stock);
                }

                //카테고리 등록
                Category parentCategory = null;
                String[] categoryNameList = categoryNames.split("\\\\u003E");
                for(String cate : categoryNameList) {
                    Category existCategory = categoryRepository.findByName(cate);

                    if(Objects.isNull(existCategory)) {
                        Category newCategory = new Category();
                        newCategory.setName(cate);
                        newCategory.setParentCategory(parentCategory);

                        categoryRepository.save(newCategory);

                        parentCategory = newCategory;
                    }else{
                        parentCategory = existCategory;
                    }

                }

                //책 - 카테고리 등록
                if(Objects.isNull(bookCategoryRepository.findByBook_bookIdAndCategory_categoryId(book.getBookId(), parentCategory.getCategoryId()))){
                    BookCategory bookCategory = new BookCategory();
                    bookCategory.setBook(book);
                    bookCategory.setCategory(parentCategory);
                    bookCategoryRepository.save(bookCategory);
                }
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
        book.setDescription(bookSaveDTO.getDescription());
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getSalePrice());
        book.setAmount(bookSaveDTO.getAmount());
        book.setPubdate(bookSaveDTO.getPubDate());

        return bookRepository.save(book);
    }
}
