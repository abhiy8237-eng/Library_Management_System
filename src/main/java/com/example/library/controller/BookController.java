package com.example.library.controller;
import com.example.library.entity.Book;
import com.example.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/books")
public class BookController {
 private final LibraryService service;
 public BookController(LibraryService s){service=s;}
 @GetMapping public List<Book> all(@RequestParam(required=false) String q){return service.searchBooks(q);}
 @PostMapping public Book add(@Valid @RequestBody Book b){return service.addBook(b);}
 @PutMapping("/{id}") public Book update(@PathVariable Long id,@Valid @RequestBody Book b){return service.updateBook(id,b);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){service.deleteBook(id);}
}
