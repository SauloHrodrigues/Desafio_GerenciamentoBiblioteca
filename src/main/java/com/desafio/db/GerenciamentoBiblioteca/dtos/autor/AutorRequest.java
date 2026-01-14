package com.desafio.db.GerenciamentoBiblioteca.dtos.autor;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

public record AutorRequest(
        String nome,
        Sexo sexo,
        String cpf,
        Integer anoDeNascimento
) {}