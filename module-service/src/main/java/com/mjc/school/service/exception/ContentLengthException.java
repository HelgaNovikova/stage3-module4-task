package com.mjc.school.service.exception;

public class ContentLengthException extends RuntimeException {

    public ContentLengthException(int minLength, int maxLength, String content) {

        super(String.format("News content can not be less than %d and more than %d symbols. News content is %s", minLength, maxLength, content));
    }
}
