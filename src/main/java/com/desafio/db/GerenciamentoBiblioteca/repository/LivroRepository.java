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
    Optional<Livro> findByTituloIgnoreCaseAndAtivoTrue(String titulo);
    Optional<Livro> findLivroByIsbnAndAtivoTrue(String isbn);
    Page<Livro> findByCategoriaAndAtivoTrue(CategoriaDeLivro categoriaDoLivro, Pageable pageable);
    Page<Livro> findByStatusLivroAndAtivoTrue(StatusLivro statusLivro, Pageable pageable);
    Page<Livro> findByAutores_IdAndAtivoTrue(Long idAutor, Pageable pageable);
    Page<Livro> findAllByAtivoTrue(Pageable pageable);
}
