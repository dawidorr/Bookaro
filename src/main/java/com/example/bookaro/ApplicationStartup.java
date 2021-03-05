package com.example.bookaro;


import com.example.bookaro.catalog.application.port.CatalogUseCase;
import com.example.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import com.example.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.example.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand.UpdateBookCommandBuilder;
import com.example.bookaro.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import com.example.bookaro.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            @Value("${bookaro.catalog.query.title}") String title,
            @Value("${bookaro.catalog.query.author}") String author,
            @Value("${bookaro.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
        catalog.addBook(new CreateBookCommand("Władca Pierścieni: Dwie Wieże", "JRR Tolkien", 1954));
        catalog.addBook(new CreateBookCommand("Mężczyżni, którzy nienawidzą kobiet", "Stieg Larsson", 2005));
        catalog.addBook(new CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013));
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1912));
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
                    System.out.println("Updating book result: "+ response.isSuccess());
                });


    }
}
