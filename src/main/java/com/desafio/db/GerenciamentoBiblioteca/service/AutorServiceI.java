package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AutorServiceI {

    AutorResponse cadastrar(AutorRequest dto);
    AutorResponse atualizar(Long id, AutorAtualiza atualizacoes);
    void apagar(Long id);
    AutorResponse buscarPorNome(String nome);
    AutorResponse buscarPorId(Long id);
    Page<AutorResponse> listarTodos(Pageable pageable);
}
