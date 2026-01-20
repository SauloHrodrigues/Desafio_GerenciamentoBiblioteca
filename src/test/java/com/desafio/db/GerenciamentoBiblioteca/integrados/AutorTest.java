package com.desafio.db.GerenciamentoBiblioteca.integrados;

import com.desafio.db.GerenciamentoBiblioteca.dtos.PageResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.AutorFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutorTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar uma pessoa com sucesso!")
    public void deveCadastrarUmaNovaPessoaComSucesso() {
        AutorRequest dto = AutorFixtures.request();
        ResponseEntity<AutorResponse> resposta = template.postForEntity(
                "/autores", dto, AutorResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(resposta.getBody());
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(dto.nome().toLowerCase());
        assertThat(resposta.getBody().cpf()).isEqualTo(dto.cpf());
        assertThat(resposta.getBody().sexo()).isEqualTo(dto.sexo());
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso!")
    public void deveAtualizarUmaPessoaComSucesso() {

        AutorRequest dto = AutorFixtures.request();
        ResponseEntity<AutorResponse> autorSalvo = template.postForEntity(
                "/autores", dto, AutorResponse.class);
        Long id = autorSalvo.getBody().id();

        AutorAtualiza atualizacao = new AutorAtualiza(
                "maria silva", null, "99955544411", 2001
        );

        ResponseEntity<AutorResponse> resposta = template.exchange(
                "/autores/{id}",
                HttpMethod.PATCH,
                new HttpEntity<>(atualizacao),
                AutorResponse.class,
                id
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();

        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(atualizacao.nome().toLowerCase());
        assertThat(resposta.getBody().cpf()).isEqualTo(atualizacao.cpf());
        assertThat(resposta.getBody().sexo()).isEqualTo(dto.sexo());
        assertThat(resposta.getBody().anoDeNascimento()).isEqualTo(atualizacao.anoDeNascimento());
    }

    @Test
    @DisplayName("Deve buscar um autor pelo nome com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveBuscarUmAutorPeloNomeComSucesso() {
        String nome = "Machado de Assis";

        ResponseEntity<AutorResponse> resposta =
                template.exchange(
                        "/autores/nome?nome={"+nome+"}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<AutorResponse>() {},
                        nome
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("Deve buscar um autor pelo id com sucesso!")
    public void deveBuscarUmAutorPeloIdComSucesso() {
        AutorRequest dto = AutorFixtures.request();
        ResponseEntity<AutorResponse> autorSalvo = template.postForEntity(
                "/autores", dto, AutorResponse.class);

        Long id = autorSalvo.getBody().id();

        ResponseEntity<AutorResponse> resposta =
                template.exchange(
                        "/autores/{"+id+"}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<AutorResponse>() {},
                        id
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isEqualTo(autorSalvo.getBody().id());
        assertThat(resposta.getBody().nome()).isEqualTo(autorSalvo.getBody().nome());
        assertThat(resposta.getBody().cpf()).isEqualTo(autorSalvo.getBody().cpf());
        assertThat(resposta.getBody().anoDeNascimento()).isEqualTo(autorSalvo.getBody().anoDeNascimento());
    }

    @Test
    @DisplayName("Deve listar todos os autores com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveListarTodosAutoresComSucesso() {
                ResponseEntity<PageResponse<AutorResponse>> resposta =
                template.exchange(
                        "/autores",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<AutorResponse>>() {}
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().content().size()).isEqualTo(3);
    }


}