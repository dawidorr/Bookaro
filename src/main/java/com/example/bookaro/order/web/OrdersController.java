package com.example.bookaro.order.web;

import com.example.bookaro.order.application.port.ManipulateOrderUseCase;
import com.example.bookaro.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import com.example.bookaro.order.application.port.QueryOrderUseCase;
import com.example.bookaro.order.application.port.QueryOrderUseCase.RichOrder;
import com.example.bookaro.order.domain.Order;
import com.example.bookaro.order.domain.OrderItem;
import com.example.bookaro.order.domain.OrderStatus;
import com.example.bookaro.order.domain.Recipient;
import com.example.bookaro.web.CreatedURI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private final QueryOrderUseCase queryOrder;
    private final ManipulateOrderUseCase manipulateOrder;

    @GetMapping
    public List<RichOrder> getOrders(){
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RichOrder> getOrderById(@PathVariable Long id){
        return queryOrder.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderCommand command){
        return manipulateOrder
                .placeOrder(command.toPlaceOrderCommand())
                .handle(
                        orderId -> ResponseEntity.created(orderUri(orderId)).build(),
                        error ->ResponseEntity.badRequest().body(error)
                );

    }
    URI orderUri(Long orderId){
        return new CreatedURI("/"+orderId).uri();
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusCommand command){
        OrderStatus orderStatus = OrderStatus
                .parseString(command.status)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown status: " + command.status)   );
        manipulateOrder.updateOrderStatus(id,orderStatus);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id){
        manipulateOrder.deleteOrderById(id);
    }

    @Data
    static class CreateOrderCommand{
        List<OrderItemCommand> items;
        RecipientCommand recipient;

        PlaceOrderCommand toPlaceOrderCommand(){
            List<OrderItem> orderItems = items
                    .stream()
                    .map(item->new OrderItem(item.bookId, item.quantity))
                    .collect(Collectors.toList());
            return new PlaceOrderCommand(orderItems,recipient.toRecipient());
        }
    }
    @Data
    static class OrderItemCommand{
        Long bookId;
        int quantity;
    }

    @Data
    static class RecipientCommand{
        String name;
        String phone;
        String street;
        String city;
        String zipCode;
        String email;

        Recipient toRecipient(){
            return new Recipient(name,phone, street, city, zipCode,email);
        }
    }

    @Data
    static class UpdateStatusCommand {
        String status;
    }
}
