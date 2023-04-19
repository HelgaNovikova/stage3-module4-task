package com.mjc.school.service.exception;

public class CommentLengthException extends RuntimeException {

    public CommentLengthException(int minLength, int maxLength, String comment) {
        super(String.format("Comment can not be less than %d and more than %d symbols. Comment is %s", minLength, maxLength, comment));
    }
}
