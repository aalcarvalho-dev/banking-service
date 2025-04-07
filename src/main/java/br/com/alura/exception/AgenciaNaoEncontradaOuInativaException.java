package br.com.alura.exception;

public class AgenciaNaoEncontradaOuInativaException extends RuntimeException{
    public AgenciaNaoEncontradaOuInativaException(String message) {
        super(message);
    }
}
