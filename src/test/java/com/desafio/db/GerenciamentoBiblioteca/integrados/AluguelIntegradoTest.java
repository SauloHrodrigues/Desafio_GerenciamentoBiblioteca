package com.desafio.db.GerenciamentoBiblioteca.integrados;

import com.desafio.db.GerenciamentoBiblioteca.dtos.PageResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.AluguelFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/gera_locatario_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AluguelIntegradoTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um aluguel com sucesso!")
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveCadastrarUmNovoAluguelComSucesso() {
        AluguelRequest dto = AluguelFixture.request();
        ResponseEntity<AluguelResponse> resposta = template.postForEntity(
                "/alugueis", dto, AluguelResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(resposta.getBody());
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().locatario()).isNotNull();
        assertThat(resposta.getBody().devolucao()).isNotNull();
        assertThat(resposta.getBody().retirada()).isNotNull();
        assertThat(resposta.getBody().livros()).isNotNull();
        assertThat(resposta.getBody().status()).isEqualTo(StatusAluguel.ATIVO);

    }


    @Test
    @DisplayName("Deve buscar os livros de deteminado aluguel com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLivroDeDeterminadoAluguelComSucesso() {
        long idAluguel = 1L;
        ResponseEntity<PageResponse<LivroResponse>> resposta =
                template.exchange(
                        "/alugueis/" + idAluguel + "/todos_livros",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<LivroResponse>>() {
                        },
                        idAluguel
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().totalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve listar todos aluguel com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveListarTodosOsAlugueisComSucesso() {

        ResponseEntity<PageResponse<AluguelResponse>> resposta =
                template.exchange(
                        "/alugueis",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<AluguelResponse>>() {
                        }
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().totalElements()).isEqualTo(6);
    }

    @Test
    @DisplayName("Deve listar todos aluguel ATIVOS com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveListarTodosOsAlugueisAtivosComSucesso() {
        String statusAluguel = "ativo";

        ResponseEntity<PageResponse<AluguelResponse>> resposta =
                template.exchange(
                        "/alugueis/status?status={statusAluguel}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<AluguelResponse>>() {
                        }, statusAluguel
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().totalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve listar todos aluguel INATIVOS com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveListarTodosOsAlugueisInativosComSucesso() {
        String statusAluguel = "inativo";

        ResponseEntity<PageResponse<AluguelResponse>> resposta =
                template.exchange(
                        "/alugueis/status?status={statusAluguel}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<AluguelResponse>>() {
                        }, statusAluguel
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().totalElements()).isEqualTo(4);
    }

    @Test
    @DisplayName("Deve buscar um aluguel pelo id com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmAluguelPeloIdComSucesso() {
        long idAluguel = 4L;

        ResponseEntity<AluguelResponse> resposta =
                template.exchange(
                        "/alugueis/{idAluguel}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<AluguelResponse>() {
                        }, idAluguel
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().locatario()).isNotNull();
        assertThat(resposta.getBody().status()).isNotNull();
        assertThat(resposta.getBody().retirada()).isNotNull();
        assertThat(resposta.getBody().devolucao()).isNotNull();
        assertThat(resposta.getBody().livros()).isNotNull();

    }

    @Test
    @DisplayName("Deve fazer a devolução de um aluguel pelo id com sucesso!")
    @Sql(scripts = {"/gera_alugueis_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_alugueis.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveDevolverUmAluguelPeloIdComSucesso() {
        long idAluguel = 1L;

        ResponseEntity<Void> resposta =
                template.exchange(
                        "/alugueis/{idAluguel}",
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<Void>() {
                        }, idAluguel
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(resposta.getBody()).isNull();
    }

}