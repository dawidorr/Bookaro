package com.example.bookaro.catalog.web;

import com.example.bookaro.catalog.application.port.CatalogUseCase;
import com.example.bookaro.catalog.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/catalog")
@RestController
@AllArgsConstructor
class CatalogController {
    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll(
            @RequestParam Optional <String> title,
            @RequestParam Optional <String> author
    ){
       if(title.isPresent() && author.isPresent()){
           return catalog.findByTitleAndAuthor(title.get(),author.get());
       }else if(title.isPresent()){
           return catalog.findByTitle(title.get());
       }else if(author.isPresent()){
           return catalog.findByAuthor(author.get());
       }else {
           return catalog.findAll();
       }

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        if(id.equals(42L)){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "I am a teapot. Sorry");
        }
        return catalog
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook(@Valid @RequestBody RestCreateBookCommand command) {
        Book book = catalog.addBook(command.toCommand());
        return ResponseEntity.created(createdBookUri(book)).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        catalog.removeById(id);
    }


    private URI createdBookUri(Book book) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + book.getId().toString()).build().toUri();
    }



    @Data
    private static class RestCreateBookCommand{

        @NotBlank(message = "Please provide a title")
        private String title;

        @NotBlank(message = "Please provide a author")
        private String author;

        @NotNull
        private Integer year;

        @NotNull(message = "Please provide a price")
        @DecimalMin(value = "0.00", message = "Please provide a price greater then or equal to 0")
        private BigDecimal price;

        CatalogUseCase.CreateBookCommand toCommand(){
            return new CatalogUseCase.CreateBookCommand(title,author,year,price);
        }
    }

}
