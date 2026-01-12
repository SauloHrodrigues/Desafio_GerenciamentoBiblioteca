package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;

import java.time.LocalDate;
import java.util.List;

public record LivroResponse(
        Long id,
        String titulo,
        String isbn,
        LocalDate dataDePublicacao,
        CategoriaDeLivro categoria,
        List<Autor> autores
) {}
