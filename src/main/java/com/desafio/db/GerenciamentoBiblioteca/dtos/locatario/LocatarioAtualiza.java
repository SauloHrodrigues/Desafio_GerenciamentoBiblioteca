package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
@Schema(name = "AluguelAtualiza", description = "Dados utilizados para atualização parcial de um locatário existente.")
public record LocatarioAtualiza(

        @Schema(description = "Nome completo do locatário", example = "João da Silva")
        String nome,

        @Schema(description = "Sexo do locatário")
        Sexo sexo,

        @Schema(description = "Telefone do locatário", example = "(11) 99999-9999")
        String telefone,

        @Email(message = "E-mail inválido.")
        @Schema(description = "E-mail do locatário", example = "joao.silva@email.com")
        String email,

        @Past(message = "A data de nascimento deve ser uma data no passado.")
        @Schema(description = "Data de nascimento do locatário", example = "1990-05-20")
        LocalDate dataDeNascimento
) {}