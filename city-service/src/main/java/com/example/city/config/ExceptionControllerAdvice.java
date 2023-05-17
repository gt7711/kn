package com.example.city.config;

import com.example.city.constants.CityConstants;
import com.example.city.exception.CityNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = {CityNotFoundException.class})
    public ResponseEntity<Object> handleCityNotFoundException(final CityNotFoundException ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put(CityConstants.TIMESTAMP, LocalDateTime.now());
        body.put(CityConstants.MESSAGE, CityConstants.CITY_NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put(CityConstants.TIMESTAMP, LocalDateTime.now());
        if (ex.getCause() instanceof ConstraintViolationException) {
            var cve = (ConstraintViolationException) ex.getCause();
            var message = "Error: " + cve.getSQLException().getMessage();
            body.put(CityConstants.MESSAGE, message);
        } else {
            body.put(CityConstants.MESSAGE, ex.getMessage());
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(final Exception ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put(CityConstants.TIMESTAMP, LocalDateTime.now());
        body.put(CityConstants.MESSAGE, CityConstants.INVALID_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final Exception ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put(CityConstants.TIMESTAMP, LocalDateTime.now());
        body.put(CityConstants.MESSAGE, CityConstants.UNAUTHORIZED_ACCESS);

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(final Exception ex) {
        var body = new LinkedHashMap<String, Object>();
        body.put(CityConstants.TIMESTAMP, LocalDateTime.now());
        body.put(CityConstants.MESSAGE, CityConstants.SOMETHING_WENT_WRONG);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
