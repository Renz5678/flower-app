package org.example.flowerapp.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, long id) {
        super(String.format("%s not found with ID : '%s'", resourceName, id));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}