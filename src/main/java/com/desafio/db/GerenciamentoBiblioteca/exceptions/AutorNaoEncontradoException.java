package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class AutorNaoEncontradoException extends RuntimeException{
    public AutorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
