package com.desafio.db.GerenciamentoBiblioteca.dtos.autor;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;
import java.util.List;

public record AutorResponse(
        Long id,
        String nome,
        Sexo sexo,
        String cpf,
        Integer anoDeNascimento,
        List<LivroResponse> livros
) {}