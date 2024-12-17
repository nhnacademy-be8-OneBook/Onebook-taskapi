package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.stock.domain.Stock;
import com.nhnacademy.taskapi.stock.repository.StockRepository;
import com.nhnacademy.taskapi.image.domain.Image;
import com.nhnacademy.taskapi.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookRegistrationServiceImpl {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Book saveBook(BookSaveDTO bookSaveDTO) {
        // 출판사 등록
        Publisher publisher = publisherRepository.findByName(bookSaveDTO.getPublisherName());
        if (publisher == null) {
            publisher = new Publisher();
            publisher.setName(bookSaveDTO.getPublisherName());
            publisherRepository.save(publisher);
        }

        // 카테고리 등록
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

        // 작가 등록
        Author author = authorRepository.findByName(bookSaveDTO.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setName(bookSaveDTO.getAuthorName());
            authorRepository.save(author);
        }

        // 태그 등록
        Tag tag = tagRepository.findByName(bookSaveDTO.getTagName());
        if (tag == null) {
            tag = new Tag();
            tag.setName(bookSaveDTO.getTagName());
            tagRepository.save(tag);
        }

        // 책 등록
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate pubdate = LocalDate.parse(bookSaveDTO.getPubdate(), formatter);

        Book book = new Book();
        book.setPublisher(publisher);
        book.setTitle(bookSaveDTO.getTitle());
        book.setDescription(bookSaveDTO.getDescription());
        book.setIsbn13(bookSaveDTO.getIsbn13());
        book.setPrice(bookSaveDTO.getPrice());
        book.setSalePrice(bookSaveDTO.getPriceSales());
        book.setAmount(bookSaveDTO.getSalesPoint());
        book.setViews(0); // 조회수 초기화
        book.setPubdate(pubdate);
        bookRepository.save(book);

        // 이미지 등록
        Image image = imageRepository.findByImageUrl(bookSaveDTO.getImageUrl())
                .orElse(null);  // Optional 처리
        if (image == null) {
            image = new Image();
            image.setImageUrl(bookSaveDTO.getImageUrl());
            image.setBook(book);
            imageRepository.save(image);
        }

        // 재고 등록
        Stock stock = stockRepository.findByBook(book)
                .orElse(null);  // Optional 처리
        if (stock == null) {
            stock = new Stock();
            stock.setBook(book);
            stock.setStock(100); // 기본 재고 100 설정
            stockRepository.save(stock);
        }

        return book;
    }
}
