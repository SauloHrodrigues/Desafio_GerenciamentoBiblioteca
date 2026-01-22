package com.desafio.db.GerenciamentoBiblioteca.exceptions;

import java.util.List;

public class AluguelAtivoException extends RuntimeException{
    private final List<String> ids;
    public AluguelAtivoException(String mensagem, List<String>ids) {
        super(mensagem +"| "+ids);
        this.ids = ids;
    }
    public List<String> getIds() {
        return ids;
    }
}
