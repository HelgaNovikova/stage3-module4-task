package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ExtraCommentController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraCommentService;
import com.mjc.school.service.dto.CommentCreateDto;
import com.mjc.school.service.dto.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController implements BaseController<CommentCreateDto, CommentResponseDto, Long>, ExtraCommentController {

    private final BaseService<CommentCreateDto, CommentResponseDto, Long> commentService;
    private final ExtraCommentService extraCommentService;


    @Autowired
    public CommentController(BaseService<CommentCreateDto, CommentResponseDto, Long> commentService, ExtraCommentService extraCommentService, ExtraCommentService extraCommentService1) {
        this.commentService = commentService;
        this.extraCommentService = extraCommentService1;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                            @RequestParam(defaultValue = "10", required = false) Integer size,
                                            @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.commentService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto readById(@PathVariable Long id) {
        return this.commentService.readById(id);
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto create(@RequestBody CommentCreateDto createRequest) {
        return this.commentService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto update(@PathVariable Long id, @RequestBody CommentCreateDto updateRequest) {
        return this.commentService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.commentService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto patchById(@PathVariable Long id, @RequestBody CommentCreateDto createRequest) {
        return this.commentService.patchById(id, createRequest);
    }

    @Override
    @GetMapping(value = "/by-news-id/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByNewsId(@PathVariable Long id) {
        return this.extraCommentService.getCommentsByNewsId(id);
    }
}
