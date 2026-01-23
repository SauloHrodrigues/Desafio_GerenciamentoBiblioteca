package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
@Schema(name = "LivroAtualiza", description = "Dados utilizados para atualização parcial de um livro.")
public record LivroAtualiza(

        @Schema(description = "Novo título do livro", example = "Memórias Póstumas de Brás Cubas")
        String titulo,

        @Schema(description = "Novo ISBN do livro", example = "9788535902778")
        String isbn,

        @Schema(description = "Nova data de publicação do livro", example = "1881-01-01")
        LocalDate dataDePublicacao,

        @Schema(description = "Nova categoria do livro")
        CategoriaDeLivro categoria
) {}