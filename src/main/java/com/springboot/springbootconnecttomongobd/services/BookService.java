package com.springboot.springbootconnecttomongobd.services;

import com.springboot.springbootconnecttomongobd.entity.Book;
import com.springboot.springbootconnecttomongobd.entity.User;
import com.springboot.springbootconnecttomongobd.execption.UserNotFoundException;
import com.springboot.springbootconnecttomongobd.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void addBook(Book book, String username) {
        try {
            User user = userService.findByUsername(username);
            Book updatedbook = bookRepository.save(book);
            user.getBook().add(updatedbook);
            userService.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

//    public void addBook(Book book) {
//        bookRepository.save(book);
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }

    public Book findById(ObjectId id) throws UserNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Book not found with id: " + id));
    }

    public Book updateBook(Book book, String id) {
        ObjectId objectId = new ObjectId(id); // Convert from String to ObjectId
        Book existingBook = bookRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Only update non-null and non-empty fields
        if (book.getTitle() != null && !book.getTitle().trim().isEmpty()) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getContent() != null && !book.getContent().trim().isEmpty()) {
            existingBook.setContent(book.getContent());
        }

        return bookRepository.save(existingBook);
    }




    @Transactional
    public void deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(userName);
            removed = user.getBook().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.addUser(user);
                bookRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

}
