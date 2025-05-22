package com.springboot.springbootconnecttomongobd.controller;


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
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable ObjectId id) {
      try{
          return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
      }catch(Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username) throws UserNotFoundException {
        User userDB = userService.findByUsername(username);
        if(userDB != null){
            userDB.setUsername(user.getUsername());
            userDB.setPassword(user.getPassword());
              userService.update(user, username);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<User> deleteUsers() {
         userService.deleteAllUsers();
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) throws UserNotFoundException {
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

