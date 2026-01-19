package com.desafio.db.GerenciamentoBiblioteca.enun;

public enum StatusAluguel {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    StatusAluguel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
