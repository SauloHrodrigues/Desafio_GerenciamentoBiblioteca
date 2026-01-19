package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class AluguelNaoEncontradoException extends RuntimeException{
    public AluguelNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
