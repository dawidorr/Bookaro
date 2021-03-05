package com.example.bookaro.catalog.application.port;

import com.example.bookaro.catalog.domain.Book;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    void addBook(CreateBookCommand command);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void removeById(Long Id);

    @Value
    class CreateBookCommand{
        String title;
        String author;
        Integer year;
    }

    @Value
    @Builder
    class UpdateBookCommand{
        Long id;
        String title;
        String author;
        Integer year;

        public Book updateFields(Book book){
            if(title != null ){
                book.setTitle(title);
            }
            if(author != null ){
                book.setAuthor(author);
            }
            if(year != null ){
                book.setYear(year);
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }
}
