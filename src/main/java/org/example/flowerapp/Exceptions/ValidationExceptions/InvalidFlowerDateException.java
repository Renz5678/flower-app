package org.example.flowerapp.Exceptions.ValidationExceptions;

public class InvalidFlowerDateException extends RuntimeException{
    public InvalidFlowerDateException(String message) {
        super(message);
    }

    public InvalidFlowerDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
