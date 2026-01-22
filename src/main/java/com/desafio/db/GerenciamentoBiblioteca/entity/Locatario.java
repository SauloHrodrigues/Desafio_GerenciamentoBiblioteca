package com.desafio.db.GerenciamentoBiblioteca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "locatarios")
public class Locatario extends Pessoa{
    @Column(name = "telefone", nullable = false)
    private String telefone;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;
    private Boolean ativo = true;

    @OneToMany(mappedBy = "locatario", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Aluguel> alugueis = new ArrayList<>();
}
