package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(name = "LocatarioRequest", description = "Dados necessários para cadastrar um novo locatario.")
public record LocatarioRequest(

        @NotBlank(message = "O nome do locatário é campo de preenchimento obrigatório.")
        @Schema(description = "Nome completo do locatário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        String nome,

        @Schema(description = "Sexo do locatário", requiredMode = Schema.RequiredMode.REQUIRED)
        Sexo sexo,

        @NotBlank(message = "O CPF é campo de preenchimento obrigatório.")
        @Pattern(regexp = "(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
        @Schema(description = "CPF do locatário", example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
        String cpf,

        @NotBlank(message = "O telefone é campo de preenchimento obrigatório.")
        @Schema(description = "Telefone do locatário", example = "(11) 99999-9999", requiredMode = Schema.RequiredMode.REQUIRED)
        String telefone,

        @NotBlank(message = "O e-mail é campo de preenchimento obrigatório.")
        @Email(message = "E-mail inválido.")
        @Schema(description = "E-mail do locatário", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @NotNull(message = "A data de nascimento é campo de preenchimento obrigatório.")
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        @Schema(description = "Data de nascimento do locatário", example = "1990-05-20", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate dataDeNascimento

) {
}