package com.data.safehaven.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthentication(AuthenticationException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciales inválidas",
                "INVALID_CREDENTIALS"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDenied(AccessDeniedException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                "No tiene permisos para realizar esta acción",
                "ACCESS_DENIED"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrity(DataIntegrityViolationException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                "La operación viola una restricción de datos (posible registro duplicado)",
                "DATA_INTEGRITY_VIOLATION"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorMessage> handleEmailException(EmailException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                "El email proporcionado ya está en uso: " + ex.getEmail(),
                "EMAIL_ALREADY_EXISTS"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "INVALID_ARGUMENT"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFound(jakarta.persistence.EntityNotFoundException ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "ENTITY_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (a, b) -> a.isEmpty() ? b : a + "; " + b);
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                "VALIDATION_ERROR"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        ErrorMessage apiError = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ha ocurrido un error inesperado. Por favor intente más tarde.",
                "INTERNAL_SERVER_ERROR"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
