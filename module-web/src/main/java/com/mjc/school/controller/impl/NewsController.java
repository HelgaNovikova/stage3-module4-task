package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraNewsController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraNewsService;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsCreateDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.dto.TagResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class NewsController implements BaseController<NewsCreateDto, NewsResponseDto, Long>, ExtraNewsController {

    private final BaseService<NewsCreateDto, NewsResponseDto, Long> newsService;
    private final ExtraNewsService extraNewsService;

    @Autowired
    public NewsController(BaseService<NewsCreateDto, NewsResponseDto, Long> newsService, ExtraNewsService extraNewsService) {
        this.newsService = newsService;
        this.extraNewsService = extraNewsService;
    }

    @Override
    @GetMapping
    public List<NewsResponseDto> readAll() {
        return this.newsService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public NewsResponseDto readById(@PathVariable Long id) {
        return this.newsService.readById(id);
    }

    @Override
    @PostMapping()
    public NewsResponseDto create(@RequestBody NewsCreateDto createRequest) {
        return this.newsService.create(createRequest);
    }

    @Override
    @PutMapping()
    public NewsResponseDto update(@RequestBody NewsCreateDto updateRequest) {
        return this.newsService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public boolean deleteById(@PathVariable Long id) {
        return this.newsService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/get-author-by-news-id/{id:\\d+}")
    public AuthorResponseDto readAuthorByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readAuthorByNewsId(id);
    }

    @Override
    @GetMapping(value = "/get-tags-by-news-id/{id:\\d+}")
    public List<TagResponseDto> readTagsByNewsId(@PathVariable Long id) {
        return this.extraNewsService.readTagsByNewsId(id);
    }

    @Override
    @GetMapping(value = "/get-news-by-parameters")
    public List<NewsResponseDto> readNewsByParams(@PathVariable List<Long> tagId,
                                                  @PathVariable String tagName,
                                                  @PathVariable String authorName,
                                                  @PathVariable String title,
                                                  @PathVariable String content) {
        return this.extraNewsService.readNewsByParams(tagId, tagName, authorName, title, content);
    }

}
