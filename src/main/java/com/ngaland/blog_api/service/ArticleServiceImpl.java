package com.ngaland.blog_api.service.impl;

import com.ngaland.blog_api.dto.ArticleRequestDTO;
import com.ngaland.blog_api.dto.ArticleResponseDTO;
import com.ngaland.blog_api.dto.CommentResponseDTO;
import com.ngaland.blog_api.model.Article;
import com.ngaland.blog_api.model.Comment;
import com.ngaland.blog_api.repository.ArticleRepository;
import com.ngaland.blog_api.service.ArticleService;
import com.ngaland.blog_api.exception.DuplicateResourceException; // Importe la nouvelle exception
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    // Méthode utilitaire pour convertir Article en ArticleResponseDTO
    private ArticleResponseDTO convertToDto(Article article) {
        List<CommentResponseDTO> commentDTOs = article.getCommentaires().stream()
                .map(this::convertCommentToDto)
                .collect(Collectors.toList());

        return new ArticleResponseDTO(
                article.getId(),
                article.getTitre(),
                article.getContenu(),
                article.getDatePublication(),
                commentDTOs
        );
    }

    // Méthode utilitaire pour convertir Comment en CommentResponseDTO
    private CommentResponseDTO convertCommentToDto(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContenu(),
                comment.getAuteur(),
                comment.getDateCommentaire(),
                comment.getArticle().getId()
        );
    }

    @Override
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequestDTO) {
        // --- VÉRIFICATION DES DOUBLONS : Titre unique ---
        articleRepository.findByTitre(articleRequestDTO.getTitre())
                .ifPresent(article -> {
                    throw new DuplicateResourceException("Un article avec ce titre existe déjà: " + articleRequestDTO.getTitre());
                });
        // --- FIN DE LA VÉRIFICATION ---

        Article article = new Article();
        article.setTitre(articleRequestDTO.getTitre());
        article.setContenu(articleRequestDTO.getContenu());

        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    @Override
    public List<ArticleResponseDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article non trouvé avec l'ID: " + id));
        return convertToDto(article);
    }

    @Override
    public ArticleResponseDTO updateArticle(Long id, ArticleRequestDTO articleRequestDTO) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article non trouvé avec l'ID: " + id));

        // --- VÉRIFICATION DES DOUBLONS LORS DE LA MISE À JOUR : Titre unique (sauf si c'est le même article) ---
        // On vérifie si un autre article (différent de celui qu'on met à jour) a déjà ce titre.
        articleRepository.findByTitre(articleRequestDTO.getTitre())
                .ifPresent(articleWithSameTitle -> {
                    if (!articleWithSameTitle.getId().equals(id)) { // Si l'article trouvé n'est PAS celui qu'on est en train de modifier
                        throw new DuplicateResourceException("Un autre article avec ce titre existe déjà: " + articleRequestDTO.getTitre());
                    }
                });
        // --- FIN DE LA VÉRIFICATION ---

        existingArticle.setTitre(articleRequestDTO.getTitre());
        existingArticle.setContenu(articleRequestDTO.getContenu());

        Article updatedArticle = articleRepository.save(existingArticle);
        return convertToDto(updatedArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article non trouvé avec l'ID: " + id);
        }
        articleRepository.deleteById(id);
    }
}