package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocatarioServiceImpl implements LocatarioServiceI {
    @Override
    public LocatarioResponse cadastrar(LocatarioRequest dto) {
        return null;
    }

    @Override
    public LocatarioResponse atualizar(Long id, LocatarioAtualiza atualizacoes) {
        return null;
    }

    @Override
    public void apagar(Long id) {

    }

    @Override
    public Page<LocatarioResponse> listarTodos(Pageable pageable) {
        return null;
    }

    @Override
    public LocatarioResponse buscarPorId(Long id) {
        return null;
    }

    @Override
    public LocatarioResponse listarTodosAlugueis(Long id) {
        return null;
    }
}
