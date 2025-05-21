package com.springboot.springbootconnecttomongobd.execption;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
