package br.com.planejaai.framework.exception;

public class SenhaIncorretaException extends RuntimeException {

    public SenhaIncorretaException() {
        super("Senha atual incorreta.");
    }

    public SenhaIncorretaException(String message) {
        super(message);
    }
}
