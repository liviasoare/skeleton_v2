package com.servustech.skeleton.exceptions;

public class InvalidConfirmTokenException extends RuntimeException{

    public InvalidConfirmTokenException(String message) {
        super(message);
    }
}
