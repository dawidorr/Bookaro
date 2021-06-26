package com.example.bookaro.catalog.db;

import com.example.bookaro.catalog.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthors_firstNameContainsIgnoreCaseOrAuthors_lastNameContainsIgnoreCase(String fistName, String lastName);


    @Query(
            " SELECT b FROM Book b JOIN b.authors a " +
                    " WHERE " +
                    " lower(a.firstName) LIKE lower(concat('%', :name,'%')) " +
                    " OR lower(a.lastName) LIKE lower(concat('%', :name,'%')) "
    )
    List<Book> findByAuthor(@Param("name")String name);
}
