package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class LivroAlugadoException extends RuntimeException{

    public LivroAlugadoException(String mensagem) {
        super(mensagem);
    }
}
