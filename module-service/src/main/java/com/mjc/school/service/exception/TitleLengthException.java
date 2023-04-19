package com.mjc.school.service.exception;

public class TitleLengthException extends RuntimeException {

    public TitleLengthException(int minLength, int maxLength, String title) {

        super(String.format("News title can not be less than %d and more than %d symbols. News title is %s", minLength, maxLength, title));
    }
}
