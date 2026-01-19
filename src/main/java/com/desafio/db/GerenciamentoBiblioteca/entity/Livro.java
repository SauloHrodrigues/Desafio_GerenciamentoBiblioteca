package com.desafio.db.GerenciamentoBiblioteca.entity;

import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(name = "data_de_publicacao", nullable = false)
    private LocalDate dataDePublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_do_livro", nullable = false)
    private CategoriaDeLivro categoria;

    @ManyToMany
    @JoinTable(
            name = "livros_autores",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @JsonIgnore
    private List<Autor> autores = new ArrayList<>();

    private StatusLivro statusLivro;

    @ManyToMany(mappedBy = "livros")
    private List<Aluguel> alugueis = new ArrayList<>();

    public void adicionarAutor(Autor autor){
        autores.add(autor);
        autor.getLivros().add(this);
    }
}
