package com.desafio.db.GerenciamentoBiblioteca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate dataNascimento;
}
