package com.desafio.db.GerenciamentoBiblioteca.dtos.autor;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(name = "AutorResponse", description = "Dados retornados de um autor.")
public record AutorResponse(

        @Schema(description = "ID do autor", example = "1")
        Long id,

        @Schema(description = "Nome completo do autor", example = "Machado de Assis")
        String nome,

        @Schema(description = "Sexo do autor", example = "MASCULINO")
        Sexo sexo,

        @Schema(description = "CPF do autor", example = "12345678901")
        String cpf,

        @Schema(description = "Ano de nascimento do autor", example = "1839")
        Integer anoDeNascimento,

        @Schema(description = "Lista de livros associados ao autor")
        List<LivroResponse> livros
) {}