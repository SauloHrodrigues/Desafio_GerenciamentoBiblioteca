package com.desafio.db.GerenciamentoBiblioteca.exceptions;

public class CpfJaExistenteException extends RuntimeException{
    public CpfJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
