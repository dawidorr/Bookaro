package com.example.bookaro;


import com.example.bookaro.catalog.application.port.CatalogUseCase;
import com.example.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.example.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.example.bookaro.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import com.example.bookaro.catalog.domain.Book;
import com.example.bookaro.order.application.port.ManipulateOrderUseCase;
import com.example.bookaro.order.application.port.QueryOrderUseCase;
import com.example.bookaro.order.domain.OrderItem;
import com.example.bookaro.order.domain.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.example.bookaro.order.application.port.ManipulateOrderUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            ManipulateOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookaro.catalog.query.title}") String title,
            @Value("${bookaro.catalog.query.author}") String author,
            @Value("${bookaro.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book chlopi = catalog.findOneByTitle("Chłopi").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        //create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armii Krajowej 31")
                .city("Krakow")
                .zipCode("30-150")
                .email("jan@example.org")
                .build();

        //place order command
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz.getId(), 16))
                .item(new OrderItem(chlopi.getId(), 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        String result = response.handle(
                orderId -> "Created ORDER with id: " + orderId,
                error -> "Failed to created order: " + error
        );
        System.out.println(result);
        //list all orders

        //queryOrder.findAll().forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: "+ order.totalPrice() + " DETAILS: " + order));
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Harry Potter i Komnata Tajemnic", "JK Rowling", 1998, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Władca Pierścieni: Dwie Wieże", "JRR Tolkien", 1954,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Mężczyżni, którzy nienawidzą kobiet", "Stieg Larsson", 2005,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1912,new BigDecimal("19.90")));
    }

    private void findByTitle() {
        List<Book> booksTitle = catalog.findByTitle(title);
        booksTitle.stream().forEach(System.out::println);
    }

    private void findByAuthor() {
        List<Book> booksAuthor = catalog.findByAuthor(author);
        booksAuthor.stream().forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book......");
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zjazd na Litwie")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("----tutaj" + command);
                    System.out.println("Updating book result: "+ response.isSuccess());
                });


    }
}
