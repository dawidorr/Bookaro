package com.example.bookaro.catalog.domain;

import java.util.List;

public interface CatalogRepository {
    List<Book> findAll();
}
