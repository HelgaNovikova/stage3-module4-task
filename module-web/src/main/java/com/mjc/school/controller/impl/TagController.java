package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagCreateDto;
import com.mjc.school.service.dto.TagResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController implements BaseController<TagCreateDto, TagResponseDto, Long> {

    private final BaseService<TagCreateDto, TagResponseDto, Long> tagService;

    public TagController(BaseService<TagCreateDto, TagResponseDto, Long> tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping
    @ResponseStatus( HttpStatus.OK )
    public List<TagResponseDto> readAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer size,
                                        @RequestParam(defaultValue = "id,asc", required = false) String sortBy){
        return this.tagService.readAll(page, size, sortBy);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public TagResponseDto readById(@PathVariable Long id) {
        return this.tagService.readById(id);
    }

    @Override
    @PostMapping()
    @ResponseStatus( HttpStatus.CREATED )
    public TagResponseDto create(@RequestBody TagCreateDto createRequest) {
        return this.tagService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public TagResponseDto update(@PathVariable Long id, @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.tagService.deleteById(id);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus( HttpStatus.OK )
    public TagResponseDto patchById(@PathVariable Long id, @RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(id, updateRequest);
    }
}
