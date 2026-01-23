package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.util.ArrayList;
import java.util.List;

public class AutorFixtures {
    public static final Long ID = 1L;
    public static final String NOME = "Paulo Roberto";
    public static final Sexo SEXO = Sexo.MASCULINO;
    public static final String CPF = "22355839034";
    public static final Integer ANO_DE_NASCIMENTO = 2000;

    public static AutorRequest request(){
        return new AutorRequest(NOME, SEXO, CPF, ANO_DE_NASCIMENTO);
    }

    public static Autor entity(){
        Autor autor = new Autor();
        autor.setId(ID);
        autor.setNome(NOME.toLowerCase());
        autor.setSexo(SEXO);
        autor.setCpf(CPF);
        autor.setAnoDeNascimento(ANO_DE_NASCIMENTO);
        return autor;
    }

    public static AutorResponse response(){
        List<LivroResponse>livos = new ArrayList<>();
        AutorResponse autor = new AutorResponse(ID, NOME.toLowerCase(), SEXO, CPF, ANO_DE_NASCIMENTO,livos);
        return autor;
    }
}
