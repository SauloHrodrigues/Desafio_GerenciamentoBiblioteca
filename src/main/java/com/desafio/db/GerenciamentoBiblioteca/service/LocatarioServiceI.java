package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocatarioServiceI {
    // cadastrar
    LocatarioResponse cadastrar(LocatarioRequest dto);
    // atualizar
    LocatarioResponse atualizar(Long id, LocatarioAtualiza atualizacoes);
    // excluir
    void apagar(Long id);
    // listar todos
    Page<LocatarioResponse> listarTodos(Pageable pageable);
    // buscar por id
    LocatarioResponse buscarPorId(Long id);
    // listar todos alugueis do locatario (entrada, saida e status do aluguel)
    LocatarioResponse listarTodosAlugueis(Long id);
}
