package com.example.bookaro.catalog.application.port;

import com.example.bookaro.catalog.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CatalogUseCase {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    Optional<Book> findOneByTitle(String title);

    Optional<Book> findOneByAuthor(String author);

    List<Book> findByAuthor(String author);


    List<Book> findByTitleAndAuthor(String title, String author);

    Book addBook(CreateBookCommand command);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void removeById(Long Id);

    void updateBookCover(UpdateBookCoverCommand command);

    void removeBookCover(Long id);

    @Value
    class UpdateBookCoverCommand {
        Long id;
        byte[] file;
        String contentType;
        String filename;
    }


    @Value
    class CreateBookCommand {
        String title;
        Set<Long> authors;
        Integer year;
        BigDecimal price;

    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateBookCommand {
        Long id;
        String title;
        Set<Long> authors;
        Integer year;
        BigDecimal price;


    }

    @Value
    class UpdateBookResponse {
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }


}
