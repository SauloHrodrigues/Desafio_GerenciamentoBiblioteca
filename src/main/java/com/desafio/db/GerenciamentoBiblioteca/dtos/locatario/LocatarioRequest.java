package com.desafio.db.GerenciamentoBiblioteca.dtos.locatario;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

public record LocatarioRequest(
        String nome,
        Sexo sexo,
        String cpf,
        String telefone,
        String email,
        LocalDate dataNascimento
) {}
