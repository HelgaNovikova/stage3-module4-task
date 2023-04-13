package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagCreateDto;
import com.mjc.school.service.dto.TagResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
public class TagController implements BaseController<TagCreateDto, TagResponseDto, Long> {

    private final BaseService<TagCreateDto, TagResponseDto, Long> tagService;

    public TagController(BaseService<TagCreateDto, TagResponseDto, Long> tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping
    public List<TagResponseDto> readAll() {
        return this.tagService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public TagResponseDto readById(@PathVariable Long id) {
        return this.tagService.readById(id);
    }

    @Override
    @PostMapping()
    public TagResponseDto create(@RequestBody TagCreateDto createRequest) {
        return this.tagService.create(createRequest);
    }

    @Override
    @PutMapping()
    public TagResponseDto update(@RequestBody TagCreateDto updateRequest) {
        return this.tagService.update(updateRequest);
    }

    @Override
    @DeleteMapping
    public boolean deleteById(@PathVariable Long id) {
        return this.tagService.deleteById(id);
    }
}
