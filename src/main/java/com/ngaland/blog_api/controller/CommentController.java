package com.ngaland.blog_api.controller;

import com.ngaland.blog_api.dto.CommentRequestDTO;
import com.ngaland.blog_api.dto.CommentResponseDTO;
import com.ngaland.blog_api.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
@RequiredArgsConstructor
@Tag(name = "Commentaires", description = "API pour la gestion des commentaires d'articles de blog")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Ajoute un commentaire à un article spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commentaire ajouté avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Un commentaire identique de cet auteur existe déjà sur cet article",
                    content = @Content)
    })
    @PostMapping // Mappe les requêtes POST sur /articles/{articleId}/comments
    public ResponseEntity<CommentResponseDTO> addCommentToArticle(
            @Parameter(description = "ID de l'article auquel ajouter le commentaire") @PathVariable Long articleId,
            @Valid @RequestBody @Parameter(description = "Objet CommentRequestDTO pour la création du commentaire")
            CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO createdComment = commentService.addCommentToArticle(articleId, commentRequestDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupère tous les commentaires d'un article spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des commentaires récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content)
    })
    @GetMapping // Mappe les requêtes GET sur /articles/{articleId}/comments
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByArticleId(
            @Parameter(description = "ID de l'article dont récupérer les commentaires") @PathVariable Long articleId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Récupère un commentaire spécifique par son ID (indépendant de l'article pour cette opération)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaire récupéré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé",
                    content = @Content)
    })
    @GetMapping("/{commentId}") // Mappe les requêtes GET sur /articles/{articleId}/comments/{commentId}
    public ResponseEntity<CommentResponseDTO> getCommentById(
            @Parameter(description = "ID de l'article (non utilisé ici, mais présent pour la cohérence du chemin)") @PathVariable Long articleId, // Gardé pour la cohérence du chemin, bien que l'ID du commentaire soit suffisant
            @Parameter(description = "ID du commentaire à récupérer") @PathVariable Long commentId) {
        // Note: Ici, nous récupérons le commentaire directement par son ID.
        // On pourrait ajouter une vérification que ce commentaire appartient bien à l'articleId donné si nécessaire.
        CommentResponseDTO comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Met à jour un commentaire existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaire mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Un commentaire identique de cet auteur existe déjà sur cet article",
                    content = @Content)
    })
    @PutMapping("/{commentId}") // Mappe les requêtes PUT sur /articles/{articleId}/comments/{commentId}
    public ResponseEntity<CommentResponseDTO> updateComment(
            @Parameter(description = "ID de l'article (non utilisé ici)") @PathVariable Long articleId, // Gardé pour la cohérence
            @Parameter(description = "ID du commentaire à mettre à jour") @PathVariable Long commentId,
            @Valid @RequestBody @Parameter(description = "Objet CommentRequestDTO avec les données de mise à jour")
            CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO updatedComment = commentService.updateComment(commentId, commentRequestDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(summary = "Supprime un commentaire par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Commentaire supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé",
                    content = @Content)
    })
    @DeleteMapping("/{commentId}") // Mappe les requêtes DELETE sur /articles/{articleId}/comments/{commentId}
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID de l'article (non utilisé ici)") @PathVariable Long articleId, // Gardé pour la cohérence
            @Parameter(description = "ID du commentaire à supprimer") @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}