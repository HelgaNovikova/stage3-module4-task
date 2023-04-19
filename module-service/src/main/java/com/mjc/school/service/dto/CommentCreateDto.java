package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public class CommentCreateDto {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Long newsId;

    public CommentCreateDto(Long id, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long newsId) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.newsId = newsId;
    }

    public CommentCreateDto(String content, Long newsId) {
        this.content = content;
        this.newsId = newsId;
    }

    public CommentCreateDto() {
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
