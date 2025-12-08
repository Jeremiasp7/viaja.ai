package br.com.viajaai.viajaai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ResponseEntity<String> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(ErroProcessamentoAtracaoException.class)
  public ResponseEntity<?> handleErroProcessamentoAtracao(ErroProcessamentoAtracaoException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

  @ExceptionHandler(OrcamentoNaoEncontradoException.class)
  public ResponseEntity<?> handleOrcamentoNaoEncontrado(OrcamentoNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(OrcamentoJaExisteException.class)
  public ResponseEntity<?> handleOrcamentoJaExiste(OrcamentoJaExisteException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(TravelPlanNaoEncontradoException.class)
  public ResponseEntity<?> handlePlanoNaoEncontrado(TravelPlanNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(ChecklistNaoEncontradoException.class)
  public ResponseEntity<?> handleChecklistNaoEncontrado(ChecklistNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(PreferenciasNaoEncontradasException.class)
  public ResponseEntity<?> handlePreferenciasNaoEncontradas(
      PreferenciasNaoEncontradasException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(AIResponseParsingException.class)
  public ResponseEntity<?> AIResponseParsingException(AIResponseParsingException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(SenhaIncorretaException.class)
  public ResponseEntity<?> SenhaIncorretaException(SenhaIncorretaException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(CriarUsuarioException.class)
  public ResponseEntity<?> CriarUsuarioException(CriarUsuarioException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
