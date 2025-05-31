package com.ngaland.blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création ou la mise à jour d'un article")
public class ArticleRequestDTO {

    @NotBlank(message = "Le titre de l'article ne peut pas être vide.")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères.")
    @Schema(description = "Titre de l'article")
    private String titre;

    @NotBlank(message = "Le contenu de l'article ne peut pas être vide.")
    @Schema(description = "Contenu de l'article", example = "Cet article explore les bases de la création d'APIs REST avec Spring Boot et JPA.")
    private String contenu;
}