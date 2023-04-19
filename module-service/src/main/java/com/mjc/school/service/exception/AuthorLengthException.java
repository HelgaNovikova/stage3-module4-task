package com.mjc.school.service.exception;

public class AuthorLengthException extends RuntimeException {

    public AuthorLengthException(int minLength, int maxLength, String name) {
        super(String.format("Author's name can not be less than %d and more than %d symbols. Author's name is %s", minLength, maxLength, name));
    }
}