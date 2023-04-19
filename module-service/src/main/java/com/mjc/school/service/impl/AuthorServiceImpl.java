package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorCreateDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsMapper;
import com.mjc.school.service.exception.AuthorNotFoundException;
import com.mjc.school.service.utils.EntitiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements BaseService<AuthorCreateDto, AuthorResponseDto, Long> {

    private final BaseRepository<AuthorModel, Long> authorRepository;

    @Autowired
    public AuthorServiceImpl(BaseRepository<AuthorModel, Long> authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public List<AuthorResponseDto> readAll(Integer page, Integer size, String sortBy) {
        var allAuthors = authorRepository.readAll(page, size, sortBy);
        List<AuthorResponseDto> authorDto = new ArrayList<>();
        for (AuthorModel item : allAuthors) {
            authorDto.add(NewsMapper.INSTANCE.authorToAuthorResponseDto(item));
        }
        return authorDto;
    }

    @Override
    @Transactional
    public AuthorResponseDto readById(Long id) {
        if (!authorRepository.existById(id)) {
            throw new AuthorNotFoundException(id);
        }
        Optional<AuthorModel> author = authorRepository.readById(id);
        AuthorModel authorModel = author.orElseThrow();
        return NewsMapper.INSTANCE.authorToAuthorResponseDto(authorModel);
    }

    @Override
    @Transactional
    public AuthorResponseDto create(AuthorCreateDto createRequest) {
        AuthorModel authorModel = NewsMapper.INSTANCE.createAuthorDtoToAuthor(createRequest);
        EntitiesValidator.validateAuthor(createRequest.getName());
        return NewsMapper.INSTANCE.authorToAuthorResponseDto(authorRepository.create(authorModel));
    }

    @Override
    @Transactional
    public AuthorResponseDto update(Long id, AuthorCreateDto updateRequest) {
        if (!authorRepository.existById(id)) {
            throw new AuthorNotFoundException(id);
        }
        Optional<AuthorModel> author = authorRepository.readById(id);
        LocalDateTime createDate = author.orElseThrow().getCreateDate();
        EntitiesValidator.validateAuthor(updateRequest.getName());
        AuthorModel authorModel = NewsMapper.INSTANCE.createAuthorDtoToAuthor(updateRequest);
        authorModel.setCreateDate(createDate);
        authorModel.setId(id);
        return NewsMapper.INSTANCE.authorToAuthorResponseDto(authorRepository.update(authorModel));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (!authorRepository.existById(id)) {
            throw new AuthorNotFoundException(id);
        }
        return authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AuthorResponseDto patchById(Long id, AuthorCreateDto updateRequest) {
        return update(id, updateRequest);
    }
}
