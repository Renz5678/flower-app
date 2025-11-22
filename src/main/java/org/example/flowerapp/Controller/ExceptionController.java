package org.example.flowerapp.Controller;

import lombok.NonNull;
import org.example.flowerapp.Exceptions.BusinessLogicExceptions.DuplicateFlowerException;
import org.example.flowerapp.Exceptions.BusinessLogicExceptions.FlowerHasDependenciesException;
import org.example.flowerapp.Exceptions.DatabaseOperationExceptions.DatabaseOperationException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.FlowerNotFoundException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.GrowthNotFoundException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.MaintenanceNotFoundException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidFlowerDataException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidGrowthDataException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidMaintenanceDataException;
import org.example.flowerapp.Models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(FlowerNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleFlowerNotFoundException(
            FlowerNotFoundException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaintenanceNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleMaintenanceNotFoundException(
            MaintenanceNotFoundException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
          HttpStatus.NOT_FOUND.value(),
          e.getMessage(),
          LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GrowthNotFoundException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleGrowthNotFoundException(
            GrowthNotFoundException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFlowerDataException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleInvalidFlowerDataException(
            InvalidFlowerDataException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMaintenanceDataException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleInvalidMaintenanceDataException(
            InvalidMaintenanceDataException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGrowthDataException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleInvalidGrowthDataException(
            InvalidGrowthDataException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleDatabaseOperationException(
            DatabaseOperationException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateFlowerException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleDuplicateFlowerException(
            DuplicateFlowerException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FlowerHasDependenciesException.class)
    public ResponseEntity<@NonNull ErrorResponse> handleFlowerHasDependenciesException(
            FlowerHasDependenciesException e, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
