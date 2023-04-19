package com.mjc.school.service.utils;

import com.mjc.school.service.exception.*;


public class EntitiesValidator {
    private static final int MIN_AUTHOR_LENGTH = 3;
    private static final int MAX_AUTHOR_LENGTH = 15;
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MIN_CONTENT_LENGTH = 5;
    private static final int MAX_CONTENT_LENGTH = 255;
    private static final int MIN_TAG_LENGTH = 3;
    private static final int MAX_TAG_LENGTH = 15;
    private static final int MIN_COMMENT_LENGTH = 5;
    private static final int MAX_COMMENT_LENGTH = 255;

    public static boolean isAuthorValid(String author) {
        return author.length() >= MIN_AUTHOR_LENGTH && author.length() <= MAX_AUTHOR_LENGTH;
    }

    public static boolean isTitleValid(String title) {
        return title.length() >= MIN_TITLE_LENGTH && title.length() <= MAX_TITLE_LENGTH;
    }

    public static boolean isContentValid(String content) {
        return content.length() >= MIN_CONTENT_LENGTH && content.length() <= MAX_CONTENT_LENGTH;
    }

    public static boolean isCommentValid(String comment) {
        return comment.length() >= MIN_COMMENT_LENGTH && comment.length() <= MAX_COMMENT_LENGTH;
    }

    public static void validateTitle(String title) {
        if (!isTitleValid(title)) {
            throw new TitleLengthException(MIN_TITLE_LENGTH, MAX_TITLE_LENGTH, title);
        }
    }

    public static void validateContent(String content) {
        if (!isContentValid(content)) {
            throw new ContentLengthException(MIN_CONTENT_LENGTH, MAX_CONTENT_LENGTH, content);
        }
    }

    public static void validateAuthor(String author) {
        if (!isAuthorValid(author)) {
            throw new AuthorLengthException(MIN_AUTHOR_LENGTH, MAX_AUTHOR_LENGTH, author);
        }
    }

    public static void validateTag(String name) {
        if (!isTagValid(name)) {
            throw new TagLengthException(MIN_TAG_LENGTH, MAX_TAG_LENGTH, name);
        }
    }

    private static boolean isTagValid(String name) {
        return name.length() >= MIN_TAG_LENGTH && name.length() <= MAX_TAG_LENGTH;
    }

    public static void validateComment(String comment) {
        if (!isCommentValid(comment)) {
            throw new CommentLengthException(MIN_COMMENT_LENGTH, MAX_COMMENT_LENGTH, comment);
        }
    }
}

