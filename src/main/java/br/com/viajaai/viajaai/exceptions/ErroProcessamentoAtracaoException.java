package br.com.viajaai.viajaai.exceptions;

public class ErroProcessamentoAtracaoException extends RuntimeException {
    public ErroProcessamentoAtracaoException(String message) {
        super(message);
    }

    public ErroProcessamentoAtracaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
