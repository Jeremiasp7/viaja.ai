package br.com.viajaai.viajaai.exceptions;

public class AIResponseParsingException extends RuntimeException {
    public AIResponseParsingException(String message) {
        super(message);
    }

    public AIResponseParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}