package com.example.bookaro.order.application.port;

import com.example.bookaro.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
