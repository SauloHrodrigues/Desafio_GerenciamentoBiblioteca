package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;

public record LivroRequest(
        String titulo,
        String isbn,
        LocalDate dataDePublicacao,
        CategoriaDeLivro categoria,
        List<Long> idAutores
) {}
