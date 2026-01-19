package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocatarioServiceI {
    LocatarioResponse cadastrar(LocatarioRequest dto);

    LocatarioResponse atualizar(Long id, LocatarioAtualiza atualizacoes);

    void apagar(Long id);

    Page<LocatarioResponse> listarTodos(Pageable pageable);

    LocatarioResponse buscarPorId(Long id);

    LocatarioResponse listarTodosAlugueis(Long id);

    Locatario buscar(Long id);
}
