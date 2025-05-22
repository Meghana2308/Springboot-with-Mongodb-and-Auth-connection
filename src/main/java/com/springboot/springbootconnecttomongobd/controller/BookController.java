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

//    @GetMapping("books/{username}")
//    public ResponseEntity<List<Book>> getAllBooks(@PathVariable String username) {
//        User user = userService.findByUsername(username);
//        List<Book> books =user.getAllBooks();
////      List<Book> books = bookService.getAllBooks();
//        if(books != null && !books.isEmpty()){
//            return new ResponseEntity<>(books, HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/books/{username}")
    public ResponseEntity<?> getAllBookOfUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Book> books = user.getBook();
        if (books != null && !books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No books found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<Book> addBooks(@RequestBody Book book, @PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            bookService.addBook(book, username);
            return new ResponseEntity<>(book, HttpStatus.OK);
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
