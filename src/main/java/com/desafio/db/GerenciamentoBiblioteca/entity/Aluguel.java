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
@Table(name = "alugueis")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "locatario_id", nullable = false)
    private Locatario locatario;

    @ManyToMany
    @JoinTable(
            name = "aluguel_livros",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros = new ArrayList<>();
    private LocalDate retirada;
    private LocalDate devolucao;

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        livro.getAlugueis().add(this);
        livro.setDisponivel(false);
    }
}
