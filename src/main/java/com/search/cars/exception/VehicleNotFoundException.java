package com.search.cars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String vin) {
        super("Vehicle with vin "+vin +" was not found!");
    }
}
