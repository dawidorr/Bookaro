package com.example.bookaro.order.application.port;

import com.example.bookaro.commons.Either;
import com.example.bookaro.order.domain.OrderItem;
import com.example.bookaro.order.domain.OrderStatus;
import com.example.bookaro.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;


public interface ManipulateOrderUseCase {
    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    void deleteOrderById(Long id);

    void updateOrderStatus(Long id, OrderStatus status);

    @Builder
    @Value
    @AllArgsConstructor
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    class PlaceOrderResponse extends Either<String, Long> {
        public PlaceOrderResponse(boolean success, String left, Long right) {
            super(success, left, right);
        }

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, null, orderId);
        }

        public static PlaceOrderResponse failure(String error) {
            return new PlaceOrderResponse(false, error, null);
        }
    }
}
