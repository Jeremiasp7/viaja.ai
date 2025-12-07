package br.com.viajaai.viajaai.exceptions;

public class SenhaIncorretaException extends RuntimeException {

  public SenhaIncorretaException() {
    super("Senha atual incorreta.");
  }

  public SenhaIncorretaException(String message) {
    super(message);
  }
}
