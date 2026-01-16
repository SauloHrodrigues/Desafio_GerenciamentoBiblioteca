package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class LivroIndisponivelException extends RuntimeException{
    public LivroIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
