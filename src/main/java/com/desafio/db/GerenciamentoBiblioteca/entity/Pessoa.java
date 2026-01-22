package com.desafio.db.GerenciamentoBiblioteca.entity;

import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "sexo")
    private Sexo sexo;
    @Column(nullable = false,  length = 12)
    private String cpf;
}
