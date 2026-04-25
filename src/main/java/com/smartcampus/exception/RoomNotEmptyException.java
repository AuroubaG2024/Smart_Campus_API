package com.smartcampus.exception;

public class RoomNotEmptyException extends RuntimeException {
    //A Constructor is used to allow an error message to be passed when the exception is thrown
    public RoomNotEmptyException(String message) {
        super(message);
    }
}
