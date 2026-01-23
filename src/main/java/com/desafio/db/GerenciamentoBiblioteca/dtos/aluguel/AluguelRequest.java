package com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;


@Schema(name = "AluguelRequest", description = "Dados necessários para cadastrar um novo aluguel.")
public record AluguelRequest(

        @Schema(description = "ID do locatário que realizará o aluguel", example = "1")
        Long idLocatario,

        @Schema( description = "Lista de IDs dos livros a serem alugados", example = "[1, 2, 3]")
        List<Long> livrosIds,

        @Schema(
                description = "Data de retirada do aluguel",
                example = "2025-01-10",
                type = "string",
                format = "date"
        )
        LocalDate retirada,

        @Schema(
                description = "Data prevista para devolução do aluguel",
                example = "2025-01-20",
                type = "string",
                format = "date"
        )
        LocalDate devolucao
) {}