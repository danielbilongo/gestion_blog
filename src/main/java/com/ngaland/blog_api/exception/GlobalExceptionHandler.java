package com.ngaland.blog_api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable; // Nécessaire pour extraire les messages de validation
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice // Indique que cette classe gère les exceptions globalement pour tous les contrôleurs
public class GlobalExceptionHandler {

    // Gère les exceptions de validation de @Valid (ex: @NotBlank, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    // Gère l'exception de ressource non trouvée (levée par nos services)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
    }

    // Gère l'exception de ressource en doublon (levée par nos services)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
    }

    // Gère toutes les autres exceptions non capturées spécifiquement
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // En environnement de production, évite de renvoyer ex.getMessage() directement pour des raisons de sécurité.
        // Tu devrais logger l'exception complète ici et renvoyer un message générique.
        System.err.println("Une erreur interne inattendue est survenue: " + ex.getMessage());
        ex.printStackTrace(); // Pour le développement, c'est utile. En prod, utilise un vrai logger.
        return new ResponseEntity<>("Une erreur interne du serveur est survenue. Veuillez réessayer plus tard.", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
}