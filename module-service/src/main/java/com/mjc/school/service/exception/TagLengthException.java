package com.mjc.school.service.exception;

public class TagLengthException extends RuntimeException {

    public TagLengthException(int minLength, int maxLength, String tag) {

        super(String.format("Tag's name can not be less than %d and more than %d symbols. Tag's name is %s", minLength, maxLength, tag));
    }
}
