package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LivroServiceI {

    LivroResponse cadastrar(LivroRequest dto);

    LivroResponse atualizar(Long id, LivroAtualiza atualizacoes);

    void apagar(Long id);

    Page<LivroResponse> listarTodos(Pageable pageable);

    LivroResponse buscarPorId(Long id);

    LivroResponse buscarPorTitulo(String titulo);

    Page<LivroResponse> buscarPorCategoria(CategoriaDeLivro categoria, Pageable pageable);

    Page<LivroResponse> buscarPorIdDoAutor(Long id, Pageable pageable);

    Livro buscar(Long id);

    void validaIsbn(String isbn);

    Livro adicionaAutores(List<Long> idAutores, Livro livro);

    void devolveLivro(Livro livro);
}