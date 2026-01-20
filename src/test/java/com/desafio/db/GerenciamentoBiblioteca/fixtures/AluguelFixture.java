package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;

import java.time.LocalDate;
import java.util.List;

public class AluguelFixture {
    public static final Long ID = 1L;
    public static final Long ID_DO_LOCATARIO = 1L;
    public static final List<Long> IDS_DOS_LIVROS = List.of(1L);
    public static final LocalDate DATA_DE_RETIRADA = LocalDate.now().plusDays(1);
    public static final LocalDate DATA_DE_DEVOLUCAO = DATA_DE_RETIRADA.plusDays(10);
    public static final Locatario LOCATARIO = LocatarioFixture.toEmtity();

    public static AluguelRequest request(){
        return new AluguelRequest(ID_DO_LOCATARIO,IDS_DOS_LIVROS,DATA_DE_RETIRADA,DATA_DE_DEVOLUCAO);
    }

    public static AluguelRequest requestSemDatas(){
        return new AluguelRequest(ID_DO_LOCATARIO,IDS_DOS_LIVROS,null,null);
    }

    public static Aluguel toEntity(){
        Aluguel aluguel = new Aluguel();
        aluguel.setId(ID);
        aluguel.setLocatario(LOCATARIO);
        aluguel.adicionarLivro(LivroFixture.entity());
        aluguel.setRetirada(DATA_DE_RETIRADA);
        aluguel.setDevolucao(DATA_DE_DEVOLUCAO);
        aluguel.setStatus(StatusAluguel.ATIVO);
        return aluguel;
    }

    public static Aluguel toEntityParaSemData(){
        Aluguel aluguel = new Aluguel();
        aluguel.setId(ID);
        aluguel.setLocatario(LOCATARIO);
        aluguel.adicionarLivro(LivroFixture.entity());
        aluguel.setRetirada(LocalDate.now());
        aluguel.setDevolucao(LocalDate.now().plusDays(2));
        return aluguel;
    }

    public static Aluguel toEntitySemLivro(){
        Aluguel aluguel = new Aluguel();
        aluguel.setId(ID);
        aluguel.setLocatario(LOCATARIO);
        aluguel.setRetirada(DATA_DE_RETIRADA);
        aluguel.setDevolucao(DATA_DE_DEVOLUCAO);
        aluguel.setStatus(StatusAluguel.ATIVO);
        return aluguel;
    }
}
