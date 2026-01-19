package com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;

import java.time.LocalDate;
import java.util.List;

public record AluguelResponse(
        Long id,
        LocalDate retirada,
        LocalDate devolucao,
        StatusAluguel status,
        LocatarioResponse locatario,
        List<LivroResponse> livros
) {}