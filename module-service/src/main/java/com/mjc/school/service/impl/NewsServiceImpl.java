package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtraNewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraNewsService;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.exception.AuthorNotFoundException;
import com.mjc.school.service.exception.NewsNotFoundException;
import com.mjc.school.service.exception.TagNotFoundException;
import com.mjc.school.service.utils.EntitiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements ExtraNewsService, BaseService<NewsCreateDto, NewsResponseDto, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;

    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final BaseRepository<TagModel, Long> tagRepository;
    private final ExtraNewsRepository extraNewsRepository;

    @Autowired
    public NewsServiceImpl(BaseRepository<NewsModel, Long> newsRepository, BaseRepository<AuthorModel, Long> authorRepository, BaseRepository<TagModel, Long> tagRepository, ExtraNewsRepository extraNewsRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.extraNewsRepository = extraNewsRepository;
    }

    @Override
    @Transactional
    public List<NewsResponseDto> readAll(Integer page, Integer size, String sortBy) {
        var allNews = newsRepository.readAll(page, size, sortBy);
        List<NewsResponseDto> newsDto = new ArrayList<>();
        for (NewsModel item : allNews) {
            newsDto.add(NewsMapper.INSTANCE.newsToNewsResponseDto(item));
        }
        return newsDto;
    }

    @Override
    @Transactional
    public NewsResponseDto readById(Long id) {
        NewsModel newsModel = newsRepository.readById(id).orElseThrow(() -> new NewsNotFoundException(id));
        return NewsMapper.INSTANCE.newsToNewsResponseDto(newsModel);
    }

    @Override
    @Transactional
    public NewsResponseDto create(NewsCreateDto createRequest) {
        AuthorModel authorModel = authorRepository.readById(createRequest.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(createRequest.getAuthorId()));

        List<TagModel> tagModels = createRequest.getTagIds().stream().map(s -> tagRepository.readById(s)
                .orElseThrow(() -> new TagNotFoundException(s))).toList();

        NewsModel news = NewsMapper.INSTANCE.createNewsDtoToNews(createRequest, authorModel, tagModels);
        EntitiesValidator.validateContent(news.getContent());
        EntitiesValidator.validateTitle(news.getTitle());
        return NewsMapper.INSTANCE.newsToNewsResponseDto(newsRepository.create(news));
    }

    @Override
    @Transactional
    public NewsResponseDto update(Long id, NewsCreateDto updateRequest) {
        EntitiesValidator.validateContent(updateRequest.getContent());
        EntitiesValidator.validateTitle(updateRequest.getTitle());
        AuthorModel authorModel = authorRepository.readById(updateRequest.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException(updateRequest.getAuthorId()));
        NewsModel news = newsRepository.readById(id).orElseThrow(() -> new NewsNotFoundException(id));

        List<TagModel> tagModels = updateRequest.getTagIds().stream().map(s -> tagRepository.readById(s)
                .orElseThrow(() -> new TagNotFoundException(s))).toList();

        LocalDateTime createDate = news.getCreateDate();
        NewsModel newsModel = NewsMapper.INSTANCE.createNewsDtoToNews(updateRequest, authorModel, tagModels);
        newsModel.setCreateDate(createDate);
        newsModel.setId(id);
        return NewsMapper.INSTANCE.newsToNewsResponseDto(newsRepository.update(newsModel));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (!newsRepository.existById(id)) {
            throw new NewsNotFoundException(id);
        }
        return newsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NewsResponseDto patchById(Long id, NewsCreateDto updateRequest) {
        NewsModel news = newsRepository.readById(id).orElseThrow(() -> new NewsNotFoundException(id));
        if (updateRequest.getAuthorId() == null) {
            updateRequest.setAuthorId(news.getAuthor().getId());
        }
        if (updateRequest.getContent() == null) {
            updateRequest.setContent(news.getContent());
        }
        if (updateRequest.getTitle() == null) {
            updateRequest.setTitle(news.getTitle());
        }
        if (updateRequest.getTagIds() == null) {
            updateRequest.setTagIds(news.getNewsTags().stream().map(TagModel::getId).toList());
        }
        return update(id, updateRequest);
    }

    @Override
    @Transactional
    public AuthorResponseDto readAuthorByNewsId(Long id) {
        NewsModel newsModel = newsRepository.readById(id).orElseThrow(() -> new NewsNotFoundException(id));
        AuthorModel author = newsModel.getAuthor();
        return NewsMapper.INSTANCE.authorToAuthorResponseDto(author);
    }

    @Override
    @Transactional
    public List<TagResponseDto> readTagsByNewsId(Long id) {
        NewsModel newsModel = newsRepository.readById(id).orElseThrow(() -> new NewsNotFoundException(id));
        List<TagModel> tags = newsModel.getNewsTags();
        List<TagResponseDto> tagDto = new ArrayList<>();
        for (TagModel item : tags) {
            tagDto.add(NewsMapper.INSTANCE.tagToTagResponseDto(item));
        }
        return tagDto;
    }

    @Override
    @Transactional
    public List<NewsResponseDto> readNewsByParams(List<Long> tagId, String tagName, String authorName, String title, String content) {
        List<NewsModel> news = extraNewsRepository.readNewsByParams(tagId, tagName, authorName, title, content);
        List<NewsResponseDto> response = new ArrayList<>();
        for (NewsModel item : news) {
            response.add(NewsMapper.INSTANCE.newsToNewsResponseDto(item));
        }
        return response;
    }
}
