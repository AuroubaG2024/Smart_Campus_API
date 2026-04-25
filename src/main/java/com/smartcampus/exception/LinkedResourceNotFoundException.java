package com.smartcampus.exception;

public class LinkedResourceNotFoundException extends RuntimeException{
    //A Constructor is used to allow an error message to be passed when the exception is thrown
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}
