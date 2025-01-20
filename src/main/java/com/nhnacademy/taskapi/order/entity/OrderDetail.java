package com.nhnacademy.taskapi.order.entity;

import com.nhnacademy.taskapi.book.domain.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @Getter
    @JoinColumn(name = "book_id")
    Book book;

    @Getter
    int bookPrice;

    @Getter
    int quantity;

    public OrderDetail(Order order, Book book, int bookPrice) {
        this.order = order;
        this.book = book;
        this.bookPrice = bookPrice;
    }
}
