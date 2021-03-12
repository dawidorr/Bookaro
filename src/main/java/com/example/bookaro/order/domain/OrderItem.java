package com.example.bookaro.order.domain;

import com.example.bookaro.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {
    Book book;
    int quantity;

}
