package br.com.viajaai.viajaai.exceptions;

public class ChecklistNaoEncontradoException extends RuntimeException {
    public ChecklistNaoEncontradoException(String message) {
        super(message);
    }
}