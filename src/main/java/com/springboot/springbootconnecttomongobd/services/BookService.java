package com.springboot.springbootconnecttomongobd.services;

import com.springboot.springbootconnecttomongobd.entity.Book;
import com.springboot.springbootconnecttomongobd.execption.UserNotFoundException;
import com.springboot.springbootconnecttomongobd.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(ObjectId id) throws UserNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Book not found with id: " + id));
    }

    public Book UpdateBook(Book book, String id) {
        ObjectId objectId = new ObjectId(id); // Convert from string to ObjectId
        Book existingBook = bookRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        if(existingBook != null){
            existingBook.setTitle(book.getTitle());
            existingBook.setContent(book.getContent());
        }
        return bookRepository.save(existingBook);
//        try {
//            User user = userService.findByUsername(username);
//            Book updatedbook = bookRepository.save(book);
//            user.getBook().add(updatedbook);
//            userService.addUser(user);
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while saving the entry.", e);
//        }
    }




    public void deleteById(ObjectId id) throws UserNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            bookRepository.deleteById(id);
        }else {
            throw new UserNotFoundException("user not found with id : "+id);
        }
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

}
