package com.example.bookaro.catalog.application;

import com.example.bookaro.catalog.application.port.AuthorUseCase;
import com.example.bookaro.catalog.db.AuthorJpaRepository;
import com.example.bookaro.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService implements AuthorUseCase {
    private final AuthorJpaRepository repository;

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }
}
