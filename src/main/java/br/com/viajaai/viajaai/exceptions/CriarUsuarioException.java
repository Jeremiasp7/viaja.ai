package br.com.viajaai.viajaai.exceptions;

public class CriarUsuarioException extends RuntimeException {
  public CriarUsuarioException(String message) {
    super(message);
  }

  public CriarUsuarioException(String message, Throwable cause) {
    super(message, cause);
  }
}
