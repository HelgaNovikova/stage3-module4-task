package com.mjc.school.repository;

import com.mjc.school.repository.model.CommentModel;

import java.util.List;

public interface ExtraCommentRepository {
    List<CommentModel> getCommentsByNewsId(Long id);
}
