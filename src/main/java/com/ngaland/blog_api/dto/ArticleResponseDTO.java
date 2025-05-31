package com.ngaland.blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la réponse d'un article")
public class ArticleResponseDTO {

    @Schema(description = "Identifiant unique de l'article", example = "1")
    private Long id;

    @Schema(description = "Titre de l'article", example = "Mon premier article sur Spring Boot")
    private String titre;

    @Schema(description = "Contenu de l'article", example = "Cet article explore les bases de la création d'APIs REST avec Spring Boot et JPA.")
    private String contenu;

    @Schema(description = "Date et heure de publication de l'article", example = "2025-05-30T10:00:00")
    private LocalDateTime datePublication;

    @Schema(description = "Liste des commentaires associés à cet article")
    private List<CommentResponseDTO> commentaires; // Inclut les commentaires sous forme de DTO
}