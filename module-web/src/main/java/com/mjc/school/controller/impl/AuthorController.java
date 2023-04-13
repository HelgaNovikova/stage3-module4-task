package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorCreateDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController implements BaseController<AuthorCreateDto, AuthorResponseDto, Long> {

    private final BaseService<AuthorCreateDto, AuthorResponseDto, Long> authorService;

    @Autowired
    public AuthorController(BaseService<AuthorCreateDto, AuthorResponseDto, Long> authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping
    public List<AuthorResponseDto> readAll() {
        return this.authorService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public AuthorResponseDto readById(@PathVariable Long id) {
        return this.authorService.readById(id);
    }

    @Override
    @PostMapping()
    public AuthorResponseDto create(@RequestBody AuthorCreateDto createRequest) {
        return this.authorService.create(createRequest);
    }

    @Override
    @PutMapping()
    public AuthorResponseDto update(@RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public boolean deleteById(@PathVariable Long id) {
        return this.authorService.deleteById(id);
    }
}
