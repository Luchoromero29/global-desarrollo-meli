package frm.utn.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

// Este @ControllerAdvice atrapa excepciones comunes
// y las traduce a respuestas HTTP más prolijas.
@ControllerAdvice
public class GlobalExceptionHandler {

    // Si el ADN es inválido (no NxN, caracteres raros, etc),
    // MutantDetector tira IllegalArgumentException.
    // Acá la convertimos en un 400 Bad Request.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        // Devolvemos el mensaje para ayudar a entender qué pasó.
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // Handler genérico para cualquier otra excepción no controlada.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        // Idealmente acá también loguearías el error.
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid JSON format");
    }
}

