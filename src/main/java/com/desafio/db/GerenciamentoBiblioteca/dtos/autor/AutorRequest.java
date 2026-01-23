package com.desafio.db.GerenciamentoBiblioteca.dtos.autor;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AutorRequest", description = "Dados necessários para cadastrar um novo autor.")
public record AutorRequest(

        @Schema(description = "Nome completo do autor", example = "Machado de Assis")
        String nome,

        @Schema(description = "Sexo do autor", example = "MASCULINO")
        Sexo sexo,

        @Schema(
                description = "CPF do autor (somente números)", example = "12345678901")
        String cpf,

        @Schema(description = "Ano de nascimento do autor", example = "1839")
        Integer anoDeNascimento
) {}
