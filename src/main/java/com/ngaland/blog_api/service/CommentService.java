package com.ngaland.blog_api.service;

import com.ngaland.blog_api.dto.CommentRequestDTO;
import com.ngaland.blog_api.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO addCommentToArticle(Long articleId, CommentRequestDTO commentRequestDTO);
    List<CommentResponseDTO> getCommentsByArticleId(Long articleId);
    CommentResponseDTO getCommentById(Long id);
    CommentResponseDTO updateComment(Long id, CommentRequestDTO commentRequestDTO);
    void deleteComment(Long id);
}