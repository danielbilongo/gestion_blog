# API de Gestion d'un Blog (Articles & Commentaires)

## Projet KFOKAM48 - API de Gestion d'un Blog

Ce projet implémente une API RESTful pour la gestion d'un blog, permettant la création, la lecture, la mise à jour et la suppression d'articles, ainsi que l'ajout de commentaires sur ces articles.

## Objectif

L'objectif principal est de fournir une interface backend pour une application de blog, gérant les entités `Article` et `Commentaire` avec des opérations CRUD complètes et une documentation API claire via Swagger.

## Fonctionnalités Implémentées

* **Articles :**
    * Créer un article (`POST /api/articles`) : Titre, Contenu, Date de publication (automatiquement générée).
    * Lire tous les articles (`GET /api/articles`).
    * Lire un article spécifique par son ID (`GET /api/articles/{id}`).
    * Mettre à jour un article existant (`PUT /api/articles/{id}`).
    * Supprimer un article (`DELETE /api/articles/{id}`).
* **Commentaires :**
    * Ajouter un commentaire sur un article spécifique (`POST /api/articles/{articleId}/commentaires`).
    * Lire tous les commentaires d'un article spécifique (`GET /api/articles/{articleId}/commentaires`).
    * Lire un commentaire spécifique par son ID (`GET /api/commentaires/{id}`).
    * Mettre à jour un commentaire (`PUT /api/commentaires/{id}`).
    * Supprimer un commentaire (`DELETE /api/commentaires/{id}`).
* **Gestion des Erreurs :**
    * Gestion centralisée des exceptions (`@ControllerAdvice`) pour retourner des réponses JSON claires en cas d'erreur (ex: ressource non trouvée).
* **Validation des Données :**
    * Validation des entrées DTO avec `jakarta.validation` pour assurer l'intégrité des données.

## Technologique

* **Spring Boot** (v3.3.0) : Cadre de travail pour le développement rapide d'applications Java.
* **Spring Data JPA** : Simplifie l'accès aux données avec Hibernate.
* **PostgreSQL** : Base de données relationnelle.
* **Lombok** : Simplifie le code Java en générant automatiquement des getters/setters, constructeurs, etc.
* **Swagger (SpringDoc OpenAPI)** (v2.6.0) : Pour la documentation interactive de l'API.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

* **Java Development Kit (JDK) 17** ou supérieur.
* **Maven** (pour la gestion des dépendances et la construction du projet).
* **PostgreSQL** : Un serveur de base de données PostgreSQL en cours d'exécution.
* **pgAdmin** (optionnel) : Un outil graphique pour gérer votre base de données PostgreSQL.

## Configuration de la Base de Données

1.  **Créer la base de données :**
    Connectez-vous à votre instance PostgreSQL et créez une base de données nommée `blogdb`.
    ```sql
    CREATE DATABASE blogdb;
    ```
2.  **Mettre à jour les identifiants :**
    Par défaut, l'application utilise les identifiants suivants dans `src/main/resources/application.properties` :
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
    spring.datasource.username=postgres
    spring.datasource.password=admin
    ```
    Si vos identifiants PostgreSQL sont différents, veuillez modifier ce fichier en conséquence.

## Démarrage de l'Application

1.  **Cloner le dépôt :**
    ```bash
    git clone [https://github.com/votre_utilisateur_github/blog_api.git](https://github.com/votre_utilisateur_github/blog_api.git)
    cd blog_api
    ```
2.  **Construire le projet avec Maven :**
    À la racine du projet (`blog_api`), exécutez la commande suivante pour compiler le code et télécharger les dépendances :
    ```bash
    mvn clean install
    ```
3.  **Lancer l'application :**
    Après un `BUILD SUCCESS`, naviguez dans le dossier `target` et exécutez le JAR généré :
    ```bash
    cd target
    java -jar blog_api-0.0.1-SNAPSHOT.jar
    ```
    L'application devrait démarrer sur le port par défaut `8080`.

## Tester l'API avec Swagger UI

Une fois l'application démarrée, ouvrez votre navigateur et accédez à :

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Vous y trouverez la documentation interactive de l'API, vous permettant de :

* **Créer des articles :** Utilisez `POST /api/articles` (voir le modèle de Request Body pour les champs `titre` et `contenu`).
* **Récupérer des articles :** Utilisez `GET /api/articles` (tous les articles) ou `GET /api/articles/{id}` (article spécifique).
* **Ajouter des commentaires :** Utilisez `POST /api/articles/{articleId}/commentaires` en spécifiant l'ID de l'article.
* **Récupérer des commentaires :** Utilisez `GET /api/articles/{articleId}/commentaires` ou `GET /api/commentaires/{id}`.
* Et bien d'autres opérations (PUT, DELETE) pour manipuler les données.


