package com.example.JobListing.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
