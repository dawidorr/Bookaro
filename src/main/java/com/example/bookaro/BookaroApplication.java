package com.example.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;


@SpringBootApplication
public class BookaroApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(BookaroApplication.class, args);
	}
	private final CatalogService catalogService;

	public BookaroApplication(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Override
	public void run(String... args) throws Exception {
//		CatalogService service = new CatalogService();
		List<Book> books = catalogService .findByTitle("Pan Tadeusz");
		books.forEach(System.out::println);
	}
}
