package com.desafio.db.GerenciamentoBiblioteca.exceptions;

import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;

import java.util.List;

public class LivrosIndisponiveisException extends RuntimeException{
    private final List<String> titulos;
    public LivrosIndisponiveisException(List<String> titulos) {
        super("Existem livros indisponíveis para aluguel.");
        this.titulos = titulos;
    }
    public List<String> getTitulos() {
        return titulos;
    }
}
