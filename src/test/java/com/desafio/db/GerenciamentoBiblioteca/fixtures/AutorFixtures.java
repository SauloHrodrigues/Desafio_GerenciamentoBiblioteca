package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AutorFixtures {
    public static final Long id = 1L;
    public static final String nome= "Paulo Roberto";
    public static final Sexo sexo = Sexo.MASCULINO;
    public static final String cpf= "22355839034";
    public static final Integer anoDeNascimento = 2000;

    public static AutorRequest request(){
        return new AutorRequest(nome,sexo,cpf,anoDeNascimento);
    }

    public static Autor entity(){
        Autor autor = new Autor();
        autor.setId(id);
        autor.setNome(nome.toLowerCase());
        autor.setSexo(sexo);
        autor.setCpf(cpf);
        autor.setAnoDeNascimento(anoDeNascimento);
        return autor;
    }

    public static AutorResponse response(){
        List<LivroResponse>livos = new ArrayList<>();
        AutorResponse autor = new AutorResponse(id,nome.toLowerCase(),sexo,cpf,anoDeNascimento,livos);
        return autor;
    }
}
