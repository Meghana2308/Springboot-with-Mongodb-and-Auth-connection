package com.springboot.springbootconnecttomongobd.controller;

import com.springboot.springbootconnecttomongobd.entity.Book;
import com.springboot.springbootconnecttomongobd.entity.User;
import com.springboot.springbootconnecttomongobd.execption.UserNotFoundException;
import com.springboot.springbootconnecttomongobd.services.BookService;
import com.springboot.springbootconnecttomongobd.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable ObjectId id) {
        try {
            return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addBooks")
    public ResponseEntity<Book> addBooks(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> UpdateBook(@PathVariable String id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.UpdateBook(book, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable ObjectId id) throws UserNotFoundException {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

}
