package com.ngaland.blog_api.model;

import jakarta.persistence.*; // Importe toutes les annotations JPA
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "commentaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Stratégie pour la génération automatique de l'ID
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // Le contenu ne peut pas être nul
    private String contenu; // Représente le contenu du commentaire

    @Column(nullable = false)
    private String auteur; // Représente l'auteur du commentaire

    @CreationTimestamp // Rempli automatiquement la date lors de la création
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCommentaire; // Date de création du commentaire

    // Relation Many-to-One avec Article
    // Plusieurs commentaires peuvent appartenir à un seul Article
    @ManyToOne(fetch = FetchType.LAZY) // Indique une relation Many-to-One. FetchType.LAZY pour charger l'article seulement quand il est nécessaire.
    @JoinColumn(name = "article_id", nullable = false) // Colonne de clé étrangère dans la table 'commentaires'
    private Article article;
}