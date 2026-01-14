package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.service.LivroServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroServiceImpl implements LivroServiceI {
    @Override
    public LivroResponse cadastrar(LivroRequest dto) {
        return null;
    }

    @Override
    public LivroResponse atualizar(Long id, LivroAtualiza atualizacoes) {
        return null;
    }

    @Override
    public void apagar(Long id) {

    }

    @Override
    public Page<LivroResponse> listarTodos(Pageable pageable) {
        return null;
    }

    @Override
    public LivroResponse buscarPorId(Long id) {
        return null;
    }

    @Override
    public Page<LivroResponse> buscarPorTitulo(String titulo,Pageable pageable) {
        return null;
    }

    @Override
    public Page<LivroResponse> buscarPorCategoria(CategoriaDeLivro categoria,Pageable pageable) {
        return null;
    }

    @Override
    public Page<LivroResponse> buscarPorIdDoAutor(Long id) {
        return null;
    }
}