package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtraNewsService;
import com.mjc.school.service.dto.AuthorCreateDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus( HttpStatus.OK )
    public List<AuthorResponseDto> readAll( @RequestParam(defaultValue = "1", required = false) Integer page,
                                            @RequestParam(defaultValue = "10", required = false) Integer size,
                                            @RequestParam(defaultValue = "id,asc", required = false) String sortBy) {
        return this.authorService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public AuthorResponseDto readById(@PathVariable Long id) {
        return this.authorService.readById(id);
    }

    @Override
    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public AuthorResponseDto create(@RequestBody AuthorCreateDto createRequest) {
        return this.authorService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public AuthorResponseDto update(@PathVariable Long id, @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.authorService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public AuthorResponseDto patchById(@PathVariable Long id, @RequestBody AuthorCreateDto updateRequest) {
        return this.authorService.update(id, updateRequest);
    }
}
