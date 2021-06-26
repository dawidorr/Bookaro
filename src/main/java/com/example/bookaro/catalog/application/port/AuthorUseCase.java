package com.example.bookaro.catalog.application.port;

import com.example.bookaro.catalog.domain.Author;

import java.util.List;

public interface AuthorUseCase {
    List<Author> findAll();
}
