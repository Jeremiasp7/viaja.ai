package br.com.viajaai.exceptions;

import br.com.planejaai.framework.exception.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, String>> handleBadCredentials(
      BadCredentialsException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ResponseEntity<Map<String, String>> handleUserNotFound(
      UsuarioNaoEncontradoException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PreferenciasNaoEncontradasException.class)
  public ResponseEntity<Map<String, String>> handlePreferencesNotFound(
      PreferenciasNaoEncontradasException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFound(
      ResourceNotFoundException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CriarUsuarioException.class)
  public ResponseEntity<Map<String, String>> handleCreateUserException(
      CriarUsuarioException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Erro ao criar usuário: " + ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SenhaIncorretaException.class)
  public ResponseEntity<Map<String, String>> handleIncorrectPassword(
      SenhaIncorretaException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AIResponseParsingException.class)
  public ResponseEntity<Map<String, String>> handleAIParsingException(
      AIResponseParsingException ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Erro ao processar resposta da IA: " + ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Erro interno do servidor: " + ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
