package com.desafio.db.GerenciamentoBiblioteca.enun;

public enum Status {
    DISPONIVEL("Disponível"),
    ALUGADO("Alugado");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
