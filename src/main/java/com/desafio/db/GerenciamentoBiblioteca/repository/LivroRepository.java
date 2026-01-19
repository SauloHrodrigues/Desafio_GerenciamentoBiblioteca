package com.desafio.db.GerenciamentoBiblioteca.repository;

import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {
    Optional<Livro> findByTituloIgnoreCase(String titulo);
    Optional<Livro> findLivroByIsbn(String isbn);
    Page<Livro> findByCategoria(CategoriaDeLivro categoriaDoLivro, Pageable pageable);
    Page<Livro> findByStatusLivro(StatusLivro statusLivro, Pageable pageable);
    Page<Livro> findByAutores_Id(Long idAutor, Pageable pageable);
}
