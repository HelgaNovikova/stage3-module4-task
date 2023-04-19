package com.mjc.school.service.exception;

public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(Long id) {

        super(" News with id " + id + " does not exist.");
    }
}
