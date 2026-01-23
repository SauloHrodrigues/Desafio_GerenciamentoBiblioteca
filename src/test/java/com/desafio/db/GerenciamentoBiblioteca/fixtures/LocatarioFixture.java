package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

public class LocatarioFixture {
    public static Long ID = 1L;
    public static String NOME = "Usuario Teste";
    public static Sexo SEXO = Sexo.MASCULINO;
    public static String CPF = "418.020.090-27";
    public static String TELEFONE = "99999999999";
    public static String EMAIL = "teste@teste.com.br";
    public static LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2000,9,3);

    public static LocatarioRequest toRequest(){
        return new LocatarioRequest(NOME, SEXO, CPF, TELEFONE, EMAIL, DATA_DE_NASCIMENTO);
    }

    public static Locatario toEmtity(){
        Locatario locatario = new Locatario();
        locatario.setId(ID);
        locatario.setNome(NOME);
        locatario.setSexo(SEXO);
        locatario.setCpf(CPF);
        locatario.setTelefone(TELEFONE);
        locatario.setEmail(EMAIL);
        locatario.setDataDeNascimento(DATA_DE_NASCIMENTO);
        return locatario;
    }

}
