package com.smartcampus.exception;

public class SensorUnavailableException extends RuntimeException {
    //A Constructor is used to allow an error message to be passed when the exception is thrown
    public SensorUnavailableException(String message) {
        super(message);
    }
}
