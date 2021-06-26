package com.example.bookaro.catalog.db;

import com.example.bookaro.catalog.domain.Author;
import com.example.bookaro.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
