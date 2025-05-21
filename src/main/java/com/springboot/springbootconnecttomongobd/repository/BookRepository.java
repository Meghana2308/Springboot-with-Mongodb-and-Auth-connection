package com.springboot.springbootconnecttomongobd.repository;

import com.springboot.springbootconnecttomongobd.entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, ObjectId> {
    void delete(Book book);
}
