package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;
import java.util.List;

public record LocatarioAlugueisResponse(
        Long id,
        String nome,
       List<AluguelResponse> alugueis
) {
}
