package com.springboot.springbootconnecttomongobd.services;

import com.springboot.springbootconnecttomongobd.entity.User;
import com.springboot.springbootconnecttomongobd.execption.UserNotFoundException;
import com.springboot.springbootconnecttomongobd.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(ObjectId id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found with Id: " + id));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User update(User user, String username) throws UserNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            return userRepository.save(userToUpdate);
        }
        throw new UserNotFoundException("User Not Found with username: " + username);
    }


    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteByUsername(String username) throws UserNotFoundException {
       Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isPresent()) {
            userRepository.deleteByUsername(username);
        }else {
            throw new UserNotFoundException("User Not Found with username: " + username);
        }

    }

}
