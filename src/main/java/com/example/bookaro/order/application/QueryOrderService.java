package com.example.bookaro.order.application;

import com.example.bookaro.order.application.port.QueryOrderUseCase;
import com.example.bookaro.order.domain.Order;
import com.example.bookaro.order.domain.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }
}
