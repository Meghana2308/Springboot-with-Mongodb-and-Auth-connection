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
            //User user = userService.findByUsername(username);
            bookService.addBook(book, username);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String username, @PathVariable ObjectId id, @RequestBody Book book) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        boolean bookBelongsToUser = user.getBook().stream().anyMatch(b -> b.getId().equals(id));
        if (!bookBelongsToUser) {
            return new ResponseEntity<>("Book does not belong to user", HttpStatus.FORBIDDEN);
        }

        Book existingBook = bookService.findById(id);
        if (existingBook == null) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }

        // Update fields
        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getContent() != null && !book.getContent().isEmpty()) {
            existingBook.setContent(book.getContent());
        }

        bookService.updateBook(existingBook, String.valueOf(id));
        return new ResponseEntity<>(existingBook, HttpStatus.OK);
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable ObjectId id, @PathVariable String username) {
        bookService.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllBooks() {
        bookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

}
