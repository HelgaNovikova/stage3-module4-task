package com.mjc.school.service.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {
        super("Author with id " + id + " does not exist.");
    }
}