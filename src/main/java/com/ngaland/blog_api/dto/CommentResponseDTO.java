package com.ngaland.blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la réponse d'un commentaire")
public class CommentResponseDTO {

    @Schema(description = "Identifiant unique du commentaire", example = "101")
    private Long id;

    @Schema(description = "Contenu du commentaire")
    private String contenu;

    @Schema(description = "Nom de l'auteur du commentaire")
    private String auteur;

    @Schema(description = "Date et heure de publication du commentaire", example = "2025-05-30T10:05:00")
    private LocalDateTime dateCommentaire;

    @Schema(description = "Identifiant de l'article auquel le commentaire est rattaché", example = "1")
    private Long articleId; // Ajout de l'ID de l'article pour contextualiser le commentaire
}