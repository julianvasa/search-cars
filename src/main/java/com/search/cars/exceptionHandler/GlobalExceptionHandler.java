package com.search.cars.exceptionHandler;

import com.search.cars.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Exception handler VehicleNotFoundException
     */
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(VehicleNotFoundException ex, WebRequest request) {
        ExceptionDetails errorDetails = new ExceptionDetails(new Date(), ex.getMessage(), request.getDescription(false), Integer.
            toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ExceptionDetails errorDetails = new ExceptionDetails(new Date(), ex.getMessage(), request.getDescription(false), Integer.
            toString(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
