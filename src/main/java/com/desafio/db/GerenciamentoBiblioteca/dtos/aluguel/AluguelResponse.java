package com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel;

import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;

import java.time.LocalDate;
import java.util.List;

public record AluguelResponse(
        Long id,
        Locatario locatario,
        List<Livro> livros,
        LocalDate retirada,
        LocalDate devolucao
) {}