package com.ngaland.blog_api.model;

import jakarta.persistence.*; // Importe toutes les annotations JPA
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titre;

    @Lob 
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @CreationTimestamp // Annotation Hibernate pour remplir automatiquement la date lors de la création de l'entité
    @Column(nullable = false, updatable = false) // La date de publication ne peut pas être nulle et n'est pas modifiable après création
    private LocalDateTime datePublication; // Représente la date de publication de l'article

    // Relation One-to-Many avec Commentaire
    // un Article peut avoir plusieurs Commentaires
    // orphanRemoval = true : Si un commentaire est retiré de la liste des commentaires de l'article, il sera supprimé de la base de données.
    // cascade = CascadeType.ALL : Toutes les opérations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) sur l'article sont propagées aux commentaires associés.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentaires = new ArrayList<>();
}