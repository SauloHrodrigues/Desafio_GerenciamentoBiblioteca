package com.desafio.db.GerenciamentoBiblioteca.repository;

import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepositry extends JpaRepository<Livro,Long> {
}
