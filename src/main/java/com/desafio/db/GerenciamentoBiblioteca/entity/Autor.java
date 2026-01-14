package com.desafio.db.GerenciamentoBiblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "autores")
public class Autor extends Pessoa {
    @Column(name = "ano_de_nascimento", nullable = false)
    private Integer anoDeNascimento;

    @ManyToMany(mappedBy = "autores")
    private List<Livro> livros = new ArrayList<>();
}
