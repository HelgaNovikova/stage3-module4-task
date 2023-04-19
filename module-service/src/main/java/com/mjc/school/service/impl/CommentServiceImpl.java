package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtraCommentRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraCommentService;
import com.mjc.school.service.dto.CommentCreateDto;
import com.mjc.school.service.dto.CommentResponseDto;
import com.mjc.school.service.dto.NewsMapper;
import com.mjc.school.service.exception.CommentNotFoundException;
import com.mjc.school.service.exception.NewsNotFoundException;
import com.mjc.school.service.utils.EntitiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements BaseService<CommentCreateDto, CommentResponseDto, Long>, ExtraCommentService {

    private final BaseRepository<CommentModel, Long> commentRepository;
    private final BaseRepository<NewsModel, Long> newsRepository;
    private final ExtraCommentRepository extraCommentRepository;

    @Autowired
    public CommentServiceImpl(BaseRepository<CommentModel, Long> commentRepository, BaseRepository<NewsModel, Long> newsRepository, ExtraCommentRepository extraCommentRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.extraCommentRepository = extraCommentRepository;
    }

    @Override
    @Transactional
    public List<CommentResponseDto> readAll(Integer page, Integer size, String sortBy) {
        var allComments = commentRepository.readAll(page, size, sortBy);
        List<CommentResponseDto> commentDto = new ArrayList<>();
        for (CommentModel item : allComments) {
            commentDto.add(NewsMapper.INSTANCE.commentToCommentResponseDto(item));
        }
        return commentDto;
    }

    @Override
    @Transactional
    public CommentResponseDto readById(Long id) {
        if (!commentRepository.existById(id)) {
            throw new CommentNotFoundException(id);
        }
        Optional<CommentModel> comment = commentRepository.readById(id);
        CommentModel commentModel = comment.orElseThrow();
        return NewsMapper.INSTANCE.commentToCommentResponseDto(commentModel);
    }

    @Override
    @Transactional
    public CommentResponseDto create(CommentCreateDto createRequest) {
        var news = newsRepository.readById(createRequest.getNewsId())
                .orElseThrow(() -> new NewsNotFoundException(createRequest.getNewsId()));
        CommentModel commentModel = NewsMapper.INSTANCE.createCommentDtoToComment(createRequest, news);
        EntitiesValidator.validateComment(createRequest.getContent());
        return NewsMapper.INSTANCE.commentToCommentResponseDto(commentRepository.create(commentModel));
    }

    @Override
    @Transactional
    public CommentResponseDto update(Long id, CommentCreateDto updateRequest) {
        CommentModel comment = commentRepository.readById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        var news = newsRepository.readById(updateRequest.getNewsId())
                .orElseThrow(() -> new NewsNotFoundException(updateRequest.getNewsId()));
        EntitiesValidator.validateComment(updateRequest.getContent());
        LocalDateTime createDate = comment.getCreateDate();
        CommentModel commentModel = NewsMapper.INSTANCE.createCommentDtoToComment(updateRequest, news);
        commentModel.setId(id);
        commentModel.setCreateDate(createDate);
        return NewsMapper.INSTANCE.commentToCommentResponseDto(commentRepository.update(commentModel));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (!commentRepository.existById(id)) {
            throw new CommentNotFoundException(id);
        }
        return commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CommentResponseDto patchById(Long id, CommentCreateDto updateRequest) {
        CommentModel comment = commentRepository.readById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        if (updateRequest.getNewsId() == null) {
            updateRequest.setNewsId(comment.getNews().getId());
        }
        if (updateRequest.getContent() == null) {
            updateRequest.setContent(comment.getContent());
        }
        return update(id, updateRequest);
    }

    @Override
    @Transactional
    public List<CommentResponseDto> getCommentsByNewsId(Long id) {
        if (!newsRepository.existById(id)) {
            throw new NewsNotFoundException(id);
        }
        var comments = extraCommentRepository.getCommentsByNewsId(id);
        List<CommentResponseDto> commentDto = new ArrayList<>();
        for (CommentModel item : comments) {
            commentDto.add(NewsMapper.INSTANCE.commentToCommentResponseDto(item));
        }
        return commentDto;
    }
}
