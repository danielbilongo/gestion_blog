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
@Schema(description = "DTO pour la création ou la mise à jour d'un commentaire")
public class CommentRequestDTO {

    @NotBlank(message = "Le contenu du commentaire ne peut pas être vide.")
    @Size(max = 1000, message = "Le commentaire ne doit pas dépasser 1000 caractères.")
    @Schema(description = "Contenu du commentaire")
    private String contenu;

    @NotBlank(message = "L'auteur du commentaire ne peut pas être vide.")
    @Size(max = 100, message = "Le nom de l'auteur ne doit pas dépasser 100 caractères.")
    @Schema(description = "Nom de l'auteur du commentaire")
    private String auteur;
}