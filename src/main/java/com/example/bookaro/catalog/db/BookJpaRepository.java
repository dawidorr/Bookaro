package com.example.bookaro.catalog.db;

import com.example.bookaro.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
