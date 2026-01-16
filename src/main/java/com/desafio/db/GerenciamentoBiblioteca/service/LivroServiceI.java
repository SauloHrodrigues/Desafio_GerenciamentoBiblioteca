package com.desafio.db.GerenciamentoBiblioteca.service;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroServiceI {
    // cadastro
    LivroResponse cadastrar(LivroRequest dto);

    //atualização
    LivroResponse atualizar(Long id, LivroAtualiza atualizacoes);

    // exclusão
    void apagar(Long id);

    // listar todos
    Page<LivroResponse> listarTodos(Pageable pageable);

    // listar pelo id
    LivroResponse buscarPorId(Long id);

    // buscar pelo titulo
    LivroResponse buscarPorTitulo(String titulo);
    // buscar pela categoria
    Page<LivroResponse> buscarPorCategoria(CategoriaDeLivro categoria, Pageable pageable);
    // listar pelo id do autor
    Page<LivroResponse> buscarPorIdDoAutor(Long id, Pageable pageable);
}