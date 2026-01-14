package com.desafio.db.GerenciamentoBiblioteca.repository;

import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatarioRepositiry extends JpaRepository<Locatario,Long> {
}
