package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
@Schema(name = "LivrResponse", description = "Representa os dados retornados de um livro.")
public record LivroResponse(

        @Schema(description = "Identificador único do livro", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Título do livro", example = "Dom Casmurro", requiredMode = Schema.RequiredMode.REQUIRED)
        String titulo,

        @Schema(description = "ISBN do livro", example = "9788535902778", requiredMode = Schema.RequiredMode.REQUIRED)
        String isbn,

        @Schema(description = "Data de publicação do livro", example = "1899-01-01")
        LocalDate dataDePublicacao,

        @Schema(description = "Categoria do livro")
        CategoriaDeLivro categoria,

        @Schema(description = "Status atual do livro")
        StatusLivro statusLivro,

        @Schema(description = "Lista de autores associados ao livro")
        List<Autor> autores
) {}