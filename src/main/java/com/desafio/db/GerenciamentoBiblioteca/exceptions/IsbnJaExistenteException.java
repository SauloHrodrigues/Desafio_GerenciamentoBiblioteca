package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class IsbnJaExistenteException extends RuntimeException{
    public IsbnJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
