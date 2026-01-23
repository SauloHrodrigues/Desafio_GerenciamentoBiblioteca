package com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(name = "AluguelResponse", description = "Representa os dados retornados de um aluguel.")
public record AluguelResponse(

        @Schema(description = "ID do aluguel", example = "10"
        )
        Long id,

        @Schema(description = "Data de retirada do aluguel",
                example = "2025-01-10",
                type = "string",
                format = "date"
        )
        LocalDate retirada,

        @Schema(description = "Data prevista de devolução do aluguel",
                example = "2025-01-20",
                type = "string",
                format = "date"
        )
        LocalDate devolucao,

        @Schema(description = "Status atual do aluguel", example = "ATIVO" )
        StatusAluguel status,

        @Schema(description = "Dados do locatário associado ao aluguel")
        LocatarioResponse locatario,

        @Schema(description = "Lista de livros associados ao aluguel")
        List<LivroResponse> livros
) {}
