package com.upday.News.web.rest;

import com.upday.News.exception.ErrorCode;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.response.ErrorFieldResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception handler controller for possible exception thrown. It takes care of identifying the exception and building a friendly response to the client.
 *
 * @see ErrorCode
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @Value("${invalid.date.format}")
    private String formatMessage;

    /**
     * Takes care of entities not found
     *
     * @return a http 400 bad request with an error code ENTITY_NOT_FOUND
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException() {
        return ResponseEntity.badRequest().body(Response.error(ErrorCode.ENTITY_NOT_FOUND));
    }

    /**
     * Takes care of required fields for specific requests.
     *
     * @param e Exception thrown
     * @return a http 400 bad request with an error code REQUIRED_FIELDS and the required fields.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleBadRequest(MethodArgumentNotValidException e) {

        List<ErrorFieldResponse> errors = e.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorFieldResponse(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());


        return ResponseEntity.badRequest().body(Response.error(ErrorCode.REQUIRED_FIELDS, errors));
    }

    /**
     * Takes care of invalid date format
     *
     * @param e Exception thrown
     * @return a http 400 bad request with an error code REQUIRED_FIELDS and the required date field invalid.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleInvalidDateFormat(MethodArgumentTypeMismatchException e) {

        if (e.getRequiredType().getCanonicalName().equalsIgnoreCase(Date.class.getCanonicalName())) {
            return ResponseEntity.badRequest().body(Response.error(ErrorCode.REQUIRED_FIELDS, new ErrorFieldResponse(e.getName(), formatMessage)));
        }

        return ResponseEntity.badRequest().build();
    }
}
