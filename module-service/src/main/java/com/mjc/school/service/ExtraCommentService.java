package com.mjc.school.service;

import com.mjc.school.service.dto.CommentResponseDto;

import java.util.List;

public interface ExtraCommentService {
    List<CommentResponseDto> getCommentsByNewsId(Long id);
}
