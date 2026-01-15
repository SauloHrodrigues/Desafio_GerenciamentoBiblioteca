package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record LocatarioResponse(
        Long id,
        String nome,
        Sexo sexo,
        String cpf,
        String telefone,
        String email,
        LocalDate dataDeNascimento
) {
}
