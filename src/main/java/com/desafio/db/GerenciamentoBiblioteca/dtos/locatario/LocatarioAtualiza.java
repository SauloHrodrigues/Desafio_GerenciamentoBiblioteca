package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

public record LocatarioAtualiza(
        String nome,
        Sexo sexo,
        String telefone,
        String email,
        LocalDate dataDeNascimento
) {}
