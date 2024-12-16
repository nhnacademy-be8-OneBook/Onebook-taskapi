package com.nhnacademy.taskapi.book.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.exception.BookTagDuplicateException;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final BookTagRepository bookTagRepository;
    private final AladinApiAdapter aladinApiAdapter;

    //알라딘 api 데이터 파싱
    @Override
    @Transactional
    public List<BookSaveDTO> saveAladin(){

        List<BookSaveDTO> dtoList = new ArrayList<>();

        String url = "https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbtjswns12211534001&QueryType=Bestseller&MaxResults=50&start=1&SearchTarget=Book&output=js&Version=20131101";

        String response = aladinApiAdapter.fetchAladinData(url);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemNode = rootNode.path("item");

            for(JsonNode item : itemNode) {
                BookSaveDTO dto = new BookSaveDTO();
                dto.setTitle(item.path("title").asText());
                dto.setAuthorName(item.path("author").asText());
                dto.setPubdate(item.path("pubDate").asText());
                dto.setDescription(item.path("description").asText());
                dto.setIsbn13(item.path("isbn13").asText());
                dto.setPriceSales(item.path("priceSales").asInt());
                dto.setPrice(item.path("priceStandard").asInt());
                dto.setCategoryNames(item.path("categoryName").asText());
                log.info("categoryName: {}", dto.getCategoryNames());
                dto.setPublisherName(item.path("publisher").asText());
                dto.setSalesPoint(item.path("salesPoint").asLong());
                dtoList.add(dto);
            }
            return dtoList;

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    // 베스트셀러 50개 정보 받아오기
    @Override
    @Transactional
    public void saveBookFromAladin() {
        List<BookSaveDTO> dtoList = saveAladin();
        for(BookSaveDTO dto :dtoList) {
            saveBook(dto);
        }
    }

    @Override
    @Transactional
    public Book saveBook(BookSaveDTO bookSaveDTO) {

        //출판사 등록
        Publisher publisher = null;
        if (Objects.isNull(publisherRepository.findByName(bookSaveDTO.getPublisherName()))) {
            throw new PublisherNotFoundException("Publisher not found !");
        } else {
            publisher = new Publisher();
            publisher.setName(bookSaveDTO.getPublisherName());
            publisher = publisherRepository.findByName(bookSaveDTO.getPublisherName());
            publisherRepository.save(publisher);
        }

        //카테고리 등록
        Category parentCategory = null;
        String[] categoryNameList = bookSaveDTO.getCategoryNames().split(">");
        for (String cate : categoryNameList) {
            Category existCategory = categoryRepository.findByName(cate);

            if (Objects.isNull(existCategory)) {
                Category newCategory = new Category();
                newCategory.setName(cate);
                newCategory.setParentCategory(parentCategory);

                categoryRepository.save(newCategory);

                parentCategory = newCategory;
            } else {
                parentCategory = existCategory;
            }

        }


        //작가 등록
        Author author = null;
        if (Objects.isNull(authorRepository.findByName(bookSaveDTO.getAuthorName()))) {
            author = new Author();
            author.setName(bookSaveDTO.getAuthorName());
            authorRepository.save(author);
        } else {
            author = authorRepository.findByName(bookSaveDTO.getAuthorName());
        }

        //태그 등록
        Tag tag = null;
        if (Objects.isNull(tagRepository.findByName(bookSaveDTO.getTagName()))) {
            tag = new Tag();
            tag.setName(bookSaveDTO.getTagName());
            tagRepository.save(tag);
        } else {
            tag = tagRepository.findByName(bookSaveDTO.getTagName());
        }


        //책 등록
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate pubdate = LocalDate.parse(bookSaveDTO.getPubdate(), formatter);

        Book book = null;
        if (Objects.isNull(bookRepository.findByIsbn13(bookSaveDTO.getIsbn13()))) {
            book = new Book();
            book.setPublisher(publisher);
            book.setTitle(bookSaveDTO.getTitle());
            book.setDescription(bookSaveDTO.getDescription());
            book.setIsbn13(bookSaveDTO.getIsbn13());
            book.setPrice(bookSaveDTO.getPrice());
            book.setSalePrice(bookSaveDTO.getPriceSales());
            book.setAmount(bookSaveDTO.getSalesPoint());
            //조회수 초기값 0 설정
            book.setViews(0);
            book.setPubdate(pubdate);

            bookRepository.save(book);

        } else {
            book = bookRepository.findByIsbn13(bookSaveDTO.getIsbn13());
        }


        //이미지 등록
        Image image = null;
        if (Objects.isNull(imageRepository.findByImageUrl(bookSaveDTO.getImageUrl()))) {
            image = new Image();
            image.setImageUrl(bookSaveDTO.getImageUrl());
            image.setBook(book);
            imageRepository.save(image);
        } else {
            image = imageRepository.findByImageUrl(bookSaveDTO.getImageUrl());
        }


        //책 - 카테고리 등록
        if (Objects.isNull(bookCategoryRepository.findByBook_bookIdAndCategory_categoryId(book.getBookId(), parentCategory.getCategoryId()))) {
            BookCategory bookCategory = new BookCategory();
            bookCategory.setBook(book);
            bookCategory.setCategory(parentCategory);
            bookCategoryRepository.save(bookCategory);
        }

        //재고 등록
        if (Objects.isNull(stockRepository.findByBook_bookId(book.getBookId()))) {
            Stock stock = new Stock();
            stock.setBook(book);
            //재고 기본 100 설정
            stock.setStock(100);
            stockRepository.save(stock);
        }


        //책-작가 등록
        BookAuthor bookAuthor = null;
        if (Objects.isNull(bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(book.getBookId(), author.getAuthorId()))) {
            bookAuthor = new BookAuthor();
            bookAuthor.setAuthor(author);
            bookAuthor.setBook(book);
            bookAuthorRepository.save(bookAuthor);
        }

        //책 - 태그 등록
        BookTag bookTag = null;
        if (Objects.isNull(bookTagRepository.findByBook_BookIdAndTag_TagId(book.getBookId(), tag.getTagId()))) {
            bookTag = new BookTag();
            bookTag.setBook(book);
            bookTag.setTag(tag);
            bookTagRepository.save(bookTag);
        } else {
            throw new BookTagDuplicateException("already exists Book_tag !");
        }
        return book;
    }

    //베스트셀러 목록 조
    @Override
    public Page<Book> bestSellerBooks(Pageable pageable) {
        return bookRepository.findTop50ByAmount(pageable);
    }
}
