package com.nhnacademy.taskapi.book.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.author.domain.Author;
import com.nhnacademy.taskapi.author.repository.AuthorRepository;
import com.nhnacademy.taskapi.book.domain.Book;
import com.nhnacademy.taskapi.book.domain.BookAuthor;
import com.nhnacademy.taskapi.book.domain.BookCategory;
import com.nhnacademy.taskapi.book.domain.BookTag;
import com.nhnacademy.taskapi.book.repository.BookRepository;
import com.nhnacademy.taskapi.book.repository.BookAuthorRepository;
import com.nhnacademy.taskapi.book.repository.BookCategoryRepository;
import com.nhnacademy.taskapi.book.repository.BookTagRepository;
import com.nhnacademy.taskapi.category.domain.Category;
import com.nhnacademy.taskapi.category.repository.CategoryRepository;
import com.nhnacademy.taskapi.dto.BookSaveDTO;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.publisher.domain.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookUpdateServiceImpl {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookTagRepository bookTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Book updateBook(Long bookId, BookSaveDTO bookSaveDTO) {
        // 기존 책 조회
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // 수정할 필드 업데이트
        if (bookSaveDTO.getTitle() != null) book.setTitle(bookSaveDTO.getTitle());
        if (bookSaveDTO.getDescription() != null) book.setDescription(bookSaveDTO.getDescription());
        if (bookSaveDTO.getPrice() != null) book.setPrice(bookSaveDTO.getPrice());
        if (bookSaveDTO.getPriceSales() != null) book.setSalePrice(bookSaveDTO.getPriceSales());
        if (bookSaveDTO.getSalesPoint() != null) book.setAmount(bookSaveDTO.getSalesPoint());
        if (bookSaveDTO.getPubdate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            book.setPubdate(LocalDate.parse(bookSaveDTO.getPubdate(), formatter));
        }

        // 출판사 업데이트
        if (bookSaveDTO.getPublisherName() != null) {
            Publisher publisher = publisherRepository.findByName(bookSaveDTO.getPublisherName());
            if (publisher != null) {
                book.setPublisher(publisher);
            }
        }

        // 카테고리 업데이트
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

        // 작가 업데이트
        Author author = authorRepository.findByName(bookSaveDTO.getAuthorName());
        if (author == null) {
            author = new Author();
            author.setName(bookSaveDTO.getAuthorName());
            authorRepository.save(author);
        }

        // 책 - 작가 등록
        BookAuthor bookAuthor = bookAuthorRepository.findByBook_bookIdAndAuthor_authorId(book.getBookId(), author.getAuthorId());
        if (bookAuthor == null) {
            bookAuthor = new BookAuthor();
            bookAuthor.setBook(book);
            bookAuthor.setAuthor(author);
            bookAuthorRepository.save(bookAuthor);
        }

        // 태그 업데이트
        Tag tag = tagRepository.findByName(bookSaveDTO.getTagName());
        if (tag == null) {
            tag = new Tag();
            tag.setName(bookSaveDTO.getTagName());
            tagRepository.save(tag);
        }

        // 책 - 태그 등록
        BookTag bookTag = bookTagRepository.findByBook_BookIdAndTag_TagId(book.getBookId(), tag.getTagId());
        if (bookTag == null) {
            bookTag = new BookTag();
            bookTag.setBook(book);
            bookTag.setTag(tag);
            bookTagRepository.save(bookTag);
        }

        return bookRepository.save(book);
    }
}
