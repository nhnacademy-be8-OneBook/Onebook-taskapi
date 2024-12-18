package com.nhnacademy.taskapi.book.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.dto.BookUpdateDTO;
import com.nhnacademy.taskapi.book.exception.BookCategoryDuplicateException;
import com.nhnacademy.taskapi.book.exception.BookDuplicateException;
import com.nhnacademy.taskapi.book.exception.BookNotFoundException;
import com.nhnacademy.taskapi.book.exception.BookTagDuplicateException;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.book.service.BookService;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.book.dto.BookSaveDTO;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.exception.StockDuplicateException;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.adapter.AladinApiAdapter;
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

    // 알라딘 API 데이터 파싱
    @Override
    @Transactional
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
                dto.setCategoryNames(item.path("categoryName").asText());
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
        Stock stock = new Stock();
        stock.setBook(book);
        stock.setStock(bookUpdateDTO.getStock());
        stockRepository.save(stock);


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
        Publisher publisher = publisherRepository.findByName(bookSaveDTO.getPublisherName());
        if (publisher == null) {
            throw new PublisherNotFoundException("Publisher not found!");
        }
        book.setPublisher(publisher);

        // 카테고리 등록
        Category parentCategory = null;
        if(Objects.nonNull(bookSaveDTO.getCategoryNames())){
            String[] categoryNameList = bookSaveDTO.getCategoryNames().split(">");
            for (String cate : categoryNameList) {
                Category existCategory = categoryRepository.findByName(cate);
                if (existCategory == null) {
                    Category newCategory = new Category();
                    newCategory.setName(cate);
                    newCategory.setParentCategory(parentCategory);
                    categoryRepository.save(newCategory);
                    parentCategory = newCategory;
                } else {
                    parentCategory = existCategory;
                }
            }
        }else{
            parentCategory = new Category();
            parentCategory.setName(bookSaveDTO.getCategoryNames());
            parentCategory.setParentCategory(null);
            categoryRepository.save(parentCategory);
        }

        // 작가 등록
        Author author = authorRepository.findByName(bookSaveDTO.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setName(bookSaveDTO.getAuthorName());
            authorRepository.save(author);
        }

        // 태그 등록
        Tag tag = null;
        if(bookSaveDTO.getTagName() != null){
            tag = tagRepository.findByName(bookSaveDTO.getTagName());
            if (tag == null) {
                tag = new Tag();
                tag.setName(bookSaveDTO.getTagName());
                tagRepository.save(tag);
            }
        }


        // 책 등록

        book.setTitle(bookSaveDTO.getTitle());
        book.setDescription(bookSaveDTO.getDescription());
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getPriceSales());
        book.setAmount(0);
        book.setViews(0);
        // 출판일 설정
        if (bookSaveDTO.getPubdate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            book.setPubdate(LocalDate.parse(bookSaveDTO.getPubdate(), formatter));
        }
        bookRepository.save(book);

        // 책-작가 연결
        BookAuthor bookAuthor = bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(book.getBookId(), author.getAuthorId());
        if (bookAuthor == null) {
            bookAuthor = new BookAuthor();
            bookAuthor.setBook(book);
            bookAuthor.setAuthor(author);
            bookAuthorRepository.save(bookAuthor);
        }

        // 책-태그 연결
        if(Objects.nonNull(tag)){

            BookTag bookTag = bookTagRepository.findByBook_BookIdAndTag_TagId(book.getBookId(), tag.getTagId());
            if (bookTag == null) {
                bookTag = new BookTag();
                bookTag.setBook(book);
                bookTag.setTag(tag);
                bookTagRepository.save(bookTag);
            } else {
                throw new BookTagDuplicateException("Book Tag already exists!");
            }
        }

        //책 - 카테고리 등록
        BookCategory bookCategory = null;
        if(Objects.nonNull(bookCategoryRepository.findByBook_bookIdAndCategory_categoryId(book.getBookId(), parentCategory.getCategoryId()))){
            throw new BookCategoryDuplicateException("Book - Category already exists!");
        }else{
            bookCategory = new BookCategory();
            bookCategory.setBook(book);
            bookCategory.setCategory(parentCategory);
            bookCategoryRepository.save(bookCategory);
        }

        // 재고 등록
        Stock stock = stockRepository.findByBook_bookId(book.getBookId());
        if (stock == null) {
            stock = new Stock();
            stock.setBook(book);
            stock.setStock(1000);
        }else{
            throw new StockDuplicateException("Stock already exists!");
        }


        return book;
    }

    // 책 삭제
    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        // 책 조회
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // 관련 이미지 삭제
        imageRepository.findByBook(book).ifPresent(imageRepository::delete);

        // 책 - 카테고리 삭제
        bookCategoryRepository.deleteByBook(book);

        // 책 - 작가 삭제
        bookAuthorRepository.deleteByBook(book);

        // 책 - 태그 삭제
        bookTagRepository.deleteByBook(book);

        // 재고 삭제
        stockRepository.findByBook(book).ifPresent(stockRepository::delete);

        // 책 삭제
        bookRepository.delete(book);

        // 출판사에 연결된 책이 없으면 출판사 삭제 (선택적)
        if (book.getPublisher() != null && book.getPublisher().getBooks().isEmpty()) {
            publisherRepository.delete(book.getPublisher());
        }
    }

    // 베스트셀러 목록 조회
    @Override
    public Page<Book> bestSellerBooks(Pageable pageable) {
        return bookRepository.findTop50ByAmount(pageable);
    }


    //책 조회
    @Override
    public Book getBook(long bookId){
        if(!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Boot not exist !");
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return book;
    }
}
