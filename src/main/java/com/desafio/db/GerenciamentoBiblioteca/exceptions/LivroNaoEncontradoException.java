package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class LivroNaoEncontradoException extends RuntimeException{
    public LivroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
