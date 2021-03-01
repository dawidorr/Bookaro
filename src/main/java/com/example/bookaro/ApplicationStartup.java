package com.example.bookaro;

import com.example.bookaro.catalog.application.CatalogController;
import com.example.bookaro.catalog.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

    @Override
    public void run(String... args)  {
        List<Book> books = catalogController.findByTitle("Pan");
        books.forEach(System.out::println);

    }
}
