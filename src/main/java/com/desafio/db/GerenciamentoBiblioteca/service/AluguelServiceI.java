package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AluguelServiceI {

    AluguelResponse cadastrar(AluguelRequest dto);

    Page<AluguelResponse>  listarLivros(Long id);

    Page<AluguelResponse> listarAlugueis(Pageable pageable);

    AluguelResponse buscarPorId(Long id);
}