package com.springboot.springbootconnecttomongobd.repository;

import com.springboot.springbootconnecttomongobd.entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<Book, ObjectId> {
     void deleteById(ObjectId id);
}
