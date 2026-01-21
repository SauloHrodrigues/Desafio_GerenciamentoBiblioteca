package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AluguelServiceI {

    AluguelResponse cadastrar(AluguelRequest dto);

    Page<LivroResponse>  listarLivros(Long id, Pageable pageable);

    Page<AluguelResponse> listarAlugueis(Pageable pageable);

    Page<AluguelResponse> listarAlugueis(String status,Pageable pageable);

    AluguelResponse buscarPorId(Long id);

    void devolverAluguel(Long id);
}