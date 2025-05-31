package com.ngaland.blog_api.controller;

import com.ngaland.blog_api.dto.ArticleRequestDTO;
import com.ngaland.blog_api.dto.ArticleResponseDTO;
import com.ngaland.blog_api.service.ArticleService;
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

@RestController // Indique que c'est un contrôleur REST
@RequestMapping("/api/v1/articles") // Chemin de base pour toutes les requêtes de ce contrôleur
@RequiredArgsConstructor // Génère un constructeur avec les champs finals
@Tag(name = "Articles", description = "API pour la gestion des articles de blog")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Crée un nouvel article de blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide (données manquantes ou format incorrect)",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Un article avec ce titre existe déjà",
                    content = @Content)
    })
    @PostMapping // Mappe les requêtes POST sur ce chemin
    public ResponseEntity<ArticleResponseDTO> createArticle(
            @Valid @RequestBody @Parameter(description = "Objet ArticleRequestDTO pour la création de l'article")
            ArticleRequestDTO articleRequestDTO) {
        ArticleResponseDTO createdArticle = articleService.createArticle(articleRequestDTO);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED); // Retourne 201 Created
    }

    @Operation(summary = "Récupère tous les articles de blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleResponseDTO.class)))
    })
    @GetMapping // Mappe les requêtes GET sur ce chemin
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {
        List<ArticleResponseDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles); // Retourne 200 OK
    }

    @Operation(summary = "Récupère un article de blog par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article récupéré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content)
    })
    @GetMapping("/{id}") // Mappe les requêtes GET sur /articles/{id}
    public ResponseEntity<ArticleResponseDTO> getArticleById(
            @Parameter(description = "ID de l'article à récupérer") @PathVariable Long id) {
        ArticleResponseDTO article = articleService.getArticleById(id);
        return ResponseEntity.ok(article); // Retourne 200 OK
    }

    @Operation(summary = "Met à jour un article de blog existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Un autre article avec ce titre existe déjà",
                    content = @Content)
    })
    @PutMapping("/{id}") // Mappe les requêtes PUT sur /articles/{id}
    public ResponseEntity<ArticleResponseDTO> updateArticle(
            @Parameter(description = "ID de l'article à mettre à jour") @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Objet ArticleRequestDTO avec les données de mise à jour")
            ArticleRequestDTO articleRequestDTO) {
        ArticleResponseDTO updatedArticle = articleService.updateArticle(id, articleRequestDTO);
        return ResponseEntity.ok(updatedArticle); // Retourne 200 OK
    }

    @Operation(summary = "Supprime un article de blog par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Article supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé",
                    content = @Content)
    })
    @DeleteMapping("/{id}") // Mappe les requêtes DELETE sur /articles/{id}
    public ResponseEntity<Void> deleteArticle(
            @Parameter(description = "ID de l'article à supprimer") @PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build(); // Retourne 204 No Content
    }
}