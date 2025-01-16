package com.nhnacademy.taskapi.book.repository;

import com.nhnacademy.taskapi.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn13(String isbn);



    //베스트셀러 목록 조회( 판매량 )
    @Query("select b from Book b order by b.amount desc ")
    Page<Book> findAllByOrderByAmount(Pageable pageable);

    Page<Book> findAllByStatusFalseOrderByPubdate(Pageable pageable);

    Page<Book> findAllByStatusFalseOrderByTitleAsc(Pageable pageable);
}
