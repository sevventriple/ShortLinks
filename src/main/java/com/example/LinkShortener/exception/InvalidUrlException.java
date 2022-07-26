package com.example.LinkShortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException() {
        super("The url is invalid, try again");
    }
}
