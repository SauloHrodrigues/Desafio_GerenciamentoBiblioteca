package com.desafio.db.GerenciamentoBiblioteca.enun;

public enum StatusLivro {
    DISPONIVEL("Disponível"),
    ALUGADO("Alugado");

    private final String descricao;

    StatusLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
