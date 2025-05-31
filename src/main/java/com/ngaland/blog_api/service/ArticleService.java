package com.ngaland.blog_api.service;

import com.ngaland.blog_api.dto.ArticleRequestDTO;
import com.ngaland.blog_api.dto.ArticleResponseDTO;

import java.util.List;

public interface ArticleService {
    ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO);
    List<ArticleResponseDTO> getAllArticles();
    ArticleResponseDTO getArticleById(Long id);
    ArticleResponseDTO updateArticle(Long id, ArticleRequestDTO articleRequestDTO);
    void deleteArticle(Long id);
}