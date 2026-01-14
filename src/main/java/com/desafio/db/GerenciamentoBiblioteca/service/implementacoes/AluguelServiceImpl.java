package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.AluguelServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AluguelServiceImpl implements AluguelServiceI {
    @Override
    public AluguelResponse cadastrar(AluguelRequest dto) {
        return null;
    }

    @Override
    public Page<AluguelResponse>  listarLivros(Long id) {
        return null;
    }

    @Override
    public Page<AluguelResponse> listarAlugueis(Pageable pageable) {
        return null;
    }

    @Override
    public AluguelResponse buscarPorId(Long id) {
        return null;
    }
}
