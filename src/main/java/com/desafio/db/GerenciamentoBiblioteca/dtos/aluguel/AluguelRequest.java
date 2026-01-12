package com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel;

import java.time.LocalDate;
import java.util.List;

public record AluguelRequest(
        Long idLocatario,
        List<Long> livrosIds,
        LocalDate retirada,
        LocalDate devolucao
) {}