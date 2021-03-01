package com.example.bookaro.catalog.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;

}
