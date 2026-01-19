package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;

import java.time.LocalDate;
import java.util.List;

public class LivroFixture {
    public static final Long ID = 1L;
    public static final String TITULO = "O Senhor dos Anéis";
    public static final String ISBN = "9788533613379";
    public static final LocalDate DATA_DE_PUBLICACAO = LocalDate.of(1954, 7, 29);
    public static final CategoriaDeLivro CATEGORIA = CategoriaDeLivro.FANTASIA;
    public static final StatusLivro STATUS_LIVRO = StatusLivro.DISPONIVEL;
    public static final Autor AUTOR_01 = AutorFixtures.entity();
    public static final Autor AUTOR_02 = AutorFixtures.entity();
    public static final List<Autor> AUTORES = List.of(AUTOR_01, AUTOR_02);

    public static LivroRequest reguest(){
        List<Long>ids = List.of(AUTOR_01.getId(),AUTOR_02.getId());
        return new LivroRequest(TITULO,ISBN,DATA_DE_PUBLICACAO,CATEGORIA,ids);
    }

    public static Livro entity(){
        Livro livro = new Livro();
        livro.setId(ID);
        livro.setTitulo(TITULO.toLowerCase());
        livro.setIsbn(ISBN);
        livro.setDataDePublicacao(DATA_DE_PUBLICACAO);
        livro.setCategoria(CATEGORIA);
        livro.adicionarAutor(AUTOR_01);
        livro.adicionarAutor(AUTOR_02);
        livro.setStatusLivro(StatusLivro.DISPONIVEL);
        return livro;
    }
}
