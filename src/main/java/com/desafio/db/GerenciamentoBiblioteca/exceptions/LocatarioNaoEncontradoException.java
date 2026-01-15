package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class LocatarioNaoEncontradoException extends RuntimeException {
    public LocatarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
