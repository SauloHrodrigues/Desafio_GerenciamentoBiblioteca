package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record LocatarioRequest(
        @NotBlank(message = "O nome do locatário é campo de preenchimento obrigatório.")
        String nome,

        Sexo sexo,

        @NotBlank(message = "O CPF é campo de preenchimento obrigatório.")
        @Pattern(
                regexp = "(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})",
                message = "O CPF deve conter exatamente 11 dígitos numéricos."
        )
        String cpf,

        @NotBlank(message = "O telefone é campo de preenchimento obrigatório.")
        String telefone,

        @NotBlank(message = "O e-mail é campo de preenchimento obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotNull(message = "A data de nascimento é campo de preenchimento obrigatório.")
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataDeNascimento
) {}
