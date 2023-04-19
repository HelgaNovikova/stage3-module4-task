package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentResponseDto;

import java.util.List;

public interface ExtraCommentController {
    List<CommentResponseDto> getCommentsByNewsId(Long id);
}
