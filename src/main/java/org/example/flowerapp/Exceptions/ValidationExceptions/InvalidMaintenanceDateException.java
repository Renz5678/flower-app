package org.example.flowerapp.Exceptions.ValidationExceptions;

public class InvalidMaintenanceDateException extends RuntimeException {

    public InvalidMaintenanceDateException(String message) {
        super(message);
    }

    public InvalidMaintenanceDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
