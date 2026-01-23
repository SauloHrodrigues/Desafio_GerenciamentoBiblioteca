package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import jakarta.persistence.Column;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
@Schema(name = "LocatarioResponse", description = "Representa os dados retornados de um locatario.")
public record LocatarioResponse(

        @Schema(description = "Identificador único do locatário", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Nome completo do locatário", example = "João da Silva")
        String nome,

        @Schema(description = "Sexo do locatário")
        Sexo sexo,

        @Schema(description = "CPF do locatário", example = "12345678901")
        String cpf,

        @Schema(description = "Telefone do locatário", example = "(11) 99999-9999")
        String telefone,

        @Schema(description = "E-mail do locatário", example = "joao.silva@email.com")
        String email,

        @Schema(description = "Data de nascimento do locatário", example = "1990-05-20")
        LocalDate dataDeNascimento
) {}