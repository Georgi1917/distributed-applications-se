package com.example.JobListing.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(value = ElementNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNoSuchElementException(ElementNotFound ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = ItemAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleItemAlreadyExists(ItemAlreadyExists ex)
    {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = InvalidLogin.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorResponse handleInvalidLogin(InvalidLogin ex)
    {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(
            MethodArgumentNotValidException ex
    )
    {

        Map<String, String> errors = new HashMap<>();

        BindingResult res = ex.getBindingResult();

        if (res.hasErrors())
        {

            for (FieldError error : res.getFieldErrors())
            {

                errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                );

            }

        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);

    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidEnumValue
            (HttpMessageNotReadableException ex)
    {

        String[] msgArr = ex.getMessage().split(": ");
        String msg = "Invalid Value. Value must be in " + msgArr[msgArr.length - 1];

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), msg);

    }

    @ExceptionHandler(value = InvalidRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidRequest
            (InvalidRequest ex)
    {

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleDataIntegrityViolation
            (DataIntegrityViolationException ex)
    {

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Item already exists");

    }

}
