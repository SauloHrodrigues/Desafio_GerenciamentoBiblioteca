package com.desafio.db.GerenciamentoBiblioteca.repository;

import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

}
