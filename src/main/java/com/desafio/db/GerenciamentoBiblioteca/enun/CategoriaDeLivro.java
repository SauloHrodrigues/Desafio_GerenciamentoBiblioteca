package com.desafio.db.GerenciamentoBiblioteca.enun;

public enum CategoriaDeLivro {
    FICCAO("Ficção"),
    FANTASIA("Fantasia"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense"),
    TERROR("Terror"),
    DRAMA("Drama"),
    AVENTURA("Aventura"),
    BIOGRAFIA("Biografia"),
    AUTOAJUDA("Autoajuda"),
    HISTORIA("História"),
    FILOSOFIA("Filosofia"),
    CIENCIA("Ciência"),
    TECNOLOGIA("Tecnologia"),
    EDUCACAO("Educação"),
    INFANTIL("Infantil"),
    JUVENIL("Juvenil"),
    POESIA("Poesia"),
    RELIGIAO("Religião"),
    ECONOMIA("Economia"),
    DIREITO("Direito"),
    POLITICA("Política"),
    HQ("Histórias em Quadrinhos"),
    MANGA("Mangá");

    private final String descricao;

    CategoriaDeLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
