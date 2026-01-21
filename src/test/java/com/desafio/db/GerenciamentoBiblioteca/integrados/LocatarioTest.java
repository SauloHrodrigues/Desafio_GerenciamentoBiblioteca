package com.desafio.db.GerenciamentoBiblioteca.integrados;

import com.desafio.db.GerenciamentoBiblioteca.dtos.PageResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.LocatarioFixture;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocatarioTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um locatario com sucesso!")
    public void deveCadastrarUmNovoLocatarioComSucesso() {
        LocatarioRequest dto = LocatarioFixture.toRequest();
        ResponseEntity<LocatarioResponse> resposta = template.postForEntity(
                "/locatarios", dto, LocatarioResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(resposta.getBody());
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(dto.nome().toLowerCase());
        assertThat(resposta.getBody().sexo()).isEqualTo(dto.sexo());
        assertThat(resposta.getBody().cpf()).isEqualTo(dto.cpf());
        assertThat(resposta.getBody().telefone()).isEqualTo(dto.telefone());
        assertThat(resposta.getBody().email()).isEqualTo(dto.email());
        assertThat(resposta.getBody().dataDeNascimento()).isEqualTo(dto.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve atualizar um locatario com sucesso!")
    @Sql(scripts = {"/gera_locatario_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveAtualizarUmLocatarioComSucesso() {
        Long id = 1L;

        LocatarioAtualiza atualizacao = new LocatarioAtualiza("Novo nome", Sexo.FEMININO, "17226587423", null, LocalDate.of(2021, 05, 05));

        ResponseEntity<LocatarioResponse> resposta = template.exchange(
                "/locatarios/{id}",
                HttpMethod.PATCH,
                new HttpEntity<>(atualizacao),
                LocatarioResponse.class,
                id
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(atualizacao.nome().toLowerCase());
        assertThat(resposta.getBody().sexo()).isEqualTo(atualizacao.sexo());
        assertThat(resposta.getBody().telefone()).isEqualTo(atualizacao.telefone());
        assertThat(resposta.getBody().email()).isNotNull();
        assertThat(resposta.getBody().dataDeNascimento()).isEqualTo(atualizacao.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve deletar um locatario do banco.")
    @Sql(scripts = {"/gera_locatario_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveDeletarUmLocatarioDoBanco() {
        long id = 1L;
        ResponseEntity<Void> resposta = template.exchange(
                "/locatarios/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(resposta.getBody()).isNull();
    }

    @Test
    @DisplayName("Deve listar todos os locatarios com sucesso!")
    @Sql(scripts = {"/gera_locatario_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveListarTodosLocatariosComSucesso() {
        ResponseEntity<PageResponse<LocatarioResponse>> resposta =
                template.exchange(
                        "/locatarios",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<LocatarioResponse>>() {
                        }
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().content().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve buscar um locatario pelo id com sucesso!")
    @Sql(scripts = {"/gera_locatario_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLocatarioPeloIdComSucesso() {
        long idDoLocatario = 1L;

        ResponseEntity<LocatarioResponse> resposta =
                template.exchange(
                        "/locatarios/{" + idDoLocatario + "}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LocatarioResponse>() {
                        },
                        idDoLocatario
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isNotNull();
        assertThat(resposta.getBody().cpf()).isNotNull();
        assertThat(resposta.getBody().sexo()).isNotNull();
        assertThat(resposta.getBody().telefone()).isNotNull();
        assertThat(resposta.getBody().email()).isNotNull();
        assertThat(resposta.getBody().dataDeNascimento()).isNotNull();

    }

}