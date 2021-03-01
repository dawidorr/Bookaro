package com.example.bookaro;

import com.example.bookaro.catalog.application.CatalogController;
import com.example.bookaro.catalog.domain.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class BookaroApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookaroApplication.class, args);
    }

}
