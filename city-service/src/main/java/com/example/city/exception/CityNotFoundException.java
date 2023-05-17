package com.example.city.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(final String fieldName, final Object fieldValue) {
        super(String.format("City not found with %s: '%s'", fieldName, fieldValue));
    }
}

