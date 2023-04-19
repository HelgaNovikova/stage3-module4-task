package com.mjc.school.service.exception;

public class CommentNotFoundException  extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super("Comment with id " + id + " does not exist.");
    }
}
