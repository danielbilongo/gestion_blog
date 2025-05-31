package com.ngaland.blog_api.service;

import com.ngaland.blog_api.dto.CommentRequestDTO;
import com.ngaland.blog_api.dto.CommentResponseDTO;
import com.ngaland.blog_api.model.Article;
import com.ngaland.blog_api.model.Comment;
import com.ngaland.blog_api.repository.ArticleRepository;
import com.ngaland.blog_api.repository.CommentRepository;
import com.ngaland.blog_api.service.CommentService;
import com.ngaland.blog_api.exception.DuplicateResourceException; // Importe la nouvelle exception
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    // Méthode utilitaire pour convertir Comment en CommentResponseDTO
    private CommentResponseDTO convertToDto(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContenu(),
                comment.getAuteur(),
                comment.getDateCommentaire(),
                comment.getArticle().getId()
        );
    }

    @Override
    public CommentResponseDTO addCommentToArticle(Long articleId, CommentRequestDTO commentRequestDTO) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article non trouvé avec l'ID: " + articleId));

        // --- VÉRIFICATION DES DOUBLONS POUR LES COMMENTAIRES ---
        // Empêche le même auteur de poster le même contenu sur le même article.
        commentRepository.findByArticleIdAndAuteurAndContenu(
                articleId,
                commentRequestDTO.getAuteur(),
                commentRequestDTO.getContenu()
        ).ifPresent(comment -> {
            throw new DuplicateResourceException("Cet auteur a déjà posté ce commentaire sur cet article.");
        });
        // --- FIN DE LA VÉRIFICATION ---

        Comment comment = new Comment();
        comment.setContenu(commentRequestDTO.getContenu());
        comment.setAuteur(commentRequestDTO.getAuteur());
        comment.setArticle(article);

        Comment savedComment = commentRepository.save(comment);
        // si on as besoi de l'article mis à jour immédiatement dans la même transaction.
        // article.getCommentaires().add(savedComment);
        // articleRepository.save(article); // Cette sauvegarde est généralement superflue avec CascadeType.ALL et mappedBy="article"

        return convertToDto(savedComment);
    }

    @Override
    public List<CommentResponseDTO> getCommentsByArticleId(Long articleId) {
        // Optionnel : Vérifier si l'article existe avant de chercher les commentaires
        if (!articleRepository.existsById(articleId)) {
            throw new EntityNotFoundException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentRepository.findByArticleId(articleId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commentaire non trouvé avec l'ID: " + id));
        return convertToDto(comment);
    }

    @Override
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO commentRequestDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commentaire non trouvé avec l'ID: " + id));

        // --- VÉRIFICATION DES DOUBLONS LORS DE LA MISE À JOUR POUR LES COMMENTAIRES ---
        // Similaire à la création, mais on doit exclure le commentaire qu'on est en train de modifier.
        commentRepository.findByArticleIdAndAuteurAndContenu(
                        existingComment.getArticle().getId(),
                        commentRequestDTO.getAuteur(),
                        commentRequestDTO.getContenu()
                )
                .ifPresent(commentWithSameDetails -> {
                    if (!commentWithSameDetails.getId().equals(id)) { // Si le commentaire trouvé n'est PAS celui qu'on met à jour
                        throw new DuplicateResourceException("Un autre commentaire avec le même auteur et contenu existe déjà sur cet article.");
                    }
                });
        // --- FIN DE LA VÉRIFICATION ---

        existingComment.setContenu(commentRequestDTO.getContenu());
        existingComment.setAuteur(commentRequestDTO.getAuteur());

        Comment updatedComment = commentRepository.save(existingComment);
        return convertToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("Commentaire non trouvé avec l'ID: " + id);
        }
        commentRepository.deleteById(id);
    }
}