package com.desafio.db.GerenciamentoBiblioteca.dtos.livro;

import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;
@Schema(name = "LivroRequest", description = "Dados utilizados para cadastrar um novo livro.")
public record LivroRequest(

        @NotBlank(message = "O título é de preenchimento obrigatório.")
        String titulo,

        @NotBlank(message = "O ISBN é campo de preenchimento obrigatório.")
        @Pattern(
                regexp = "^(?:\\D*\\d){13}\\D*$",
                message = "ISBN inválido. O ISBN deve conter exatamente 13 dígitos."
        )
        String isbn,

        @NotNull(message = "A data de publicação é obrigatória.")
        LocalDate dataDePublicacao,

        @NotNull(message = "A categoria do livro é obrigatória.")
        CategoriaDeLivro categoria,

        @NotEmpty(message = "É obrigatório informar ao menos um autor.")
        List<Long> idAutores
) {

        public LivroRequest {
                if (isbn != null) {
                        isbn = isbn.replaceAll("\\D", "");
                }
        }
}