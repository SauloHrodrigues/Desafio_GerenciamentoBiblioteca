package com.desafio.db.GerenciamentoBiblioteca.fixtures;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;

import java.time.LocalDate;

public class LocatarioFixture {
    public static Long id= 1L;
    public static String nome = "Usuario Teste";
    public static Sexo sexo= Sexo.MASCULINO;
    public static String cpf = "418.020.090-27";
    public static String telefone = "99999999999";
    public static String email= "teste@teste.com.br";
    public static LocalDate dataDeNascimento = LocalDate.of(2000,9,3);

    public static LocatarioRequest toRequest(){
        return new LocatarioRequest(nome,sexo,cpf,telefone,email,dataDeNascimento);
    }

    public static Locatario toEmtity(){
        Locatario locatario = new Locatario();
        locatario.setId(id);
        locatario.setNome(nome);
        locatario.setSexo(sexo);
        locatario.setCpf(cpf);
        locatario.setTelefone(telefone);
        locatario.setEmail(email);
        locatario.setDataDeNascimento(dataDeNascimento);
        return locatario;
    }

}
