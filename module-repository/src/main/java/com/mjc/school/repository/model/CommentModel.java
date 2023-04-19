package com.mjc.school.repository.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
public class CommentModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "lastModifiedDate")
    private LocalDateTime lastUpdateDate;

    @ManyToOne
    @JoinColumn(name = "newsId")
    private NewsModel news;

    public NewsModel getNews() {
        return news;
    }

    public void setNews(NewsModel news) {
        this.news = news;
    }

    public CommentModel(Long id, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, NewsModel news) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.news = news;
    }

    public CommentModel() {
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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
