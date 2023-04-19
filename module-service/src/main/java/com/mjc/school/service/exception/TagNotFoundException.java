package com.mjc.school.service.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(Long id) {

        super("Tag with id " + id + " does not exist.");
    }
}
