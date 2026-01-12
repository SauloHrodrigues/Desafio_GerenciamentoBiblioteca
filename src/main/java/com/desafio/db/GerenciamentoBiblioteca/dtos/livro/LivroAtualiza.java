package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;

import java.time.LocalDate;
import java.util.List;

public record LivroAtualiza(
        String titulo,
        String isbn,
        LocalDate dataDePublicacao,
        CategoriaDeLivro categoria,
        List<Long> idAutores
) {}
