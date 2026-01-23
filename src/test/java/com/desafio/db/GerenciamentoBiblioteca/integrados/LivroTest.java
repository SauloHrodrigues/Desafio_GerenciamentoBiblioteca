package com.desafio.db.GerenciamentoBiblioteca.integrados;

import com.desafio.db.GerenciamentoBiblioteca.dtos.PageResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.LivroFixture;
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
@Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class LivroTest {
    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveCadastrarUmNovoLivroComSucesso() {
        LivroRequest dto = LivroFixture.request();
        ResponseEntity<LivroResponse> resposta = template.postForEntity(
                "/livros", dto, LivroResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(resposta.getBody());
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().titulo()).isEqualTo(dto.titulo().toLowerCase());
        assertThat(resposta.getBody().isbn()).isEqualTo(dto.isbn());
        assertThat(resposta.getBody().dataDePublicacao()).isEqualTo(dto.dataDePublicacao());
        assertThat(resposta.getBody().categoria()).isEqualTo(dto.categoria());
        assertThat(resposta.getBody().statusLivro()).isEqualTo(StatusLivro.DISPONIVEL);

    }


    @Test
    @DisplayName("Deve atualizar um livro com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveAtualizarUmLivroComSucesso() {
        LivroRequest dto = LivroFixture.request();
        ResponseEntity<LivroResponse> livroSalvo = template.postForEntity(
                "/livros", dto, LivroResponse.class);
        System.out.println(livroSalvo);
        Long id = livroSalvo.getBody().id();

        LivroAtualiza atualizacao = new LivroAtualiza("novo titulo", null, LocalDate.now(), null);

        ResponseEntity<LivroResponse> resposta = template.exchange(
                "/livros/{id}",
                HttpMethod.PATCH,
                new HttpEntity<>(atualizacao),
                LivroResponse.class,
                id
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().titulo()).isEqualTo(atualizacao.titulo());
        assertThat(resposta.getBody().isbn()).isEqualTo(dto.isbn());
        assertThat(resposta.getBody().dataDePublicacao()).isEqualTo(atualizacao.dataDePublicacao());
        assertThat(resposta.getBody().categoria()).isEqualTo(dto.categoria());
    }

    @Test
    @DisplayName("Deve deletar um livro do banco.")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveDeletarUmLivroDoBanco() {
        LivroRequest dto = LivroFixture.request();
        ResponseEntity<LivroResponse> livroSalvo = template.postForEntity(
                "/livros", dto, LivroResponse.class);
        Long id = livroSalvo.getBody().id();

        ResponseEntity<Void> resposta = template.exchange(
                "/livros/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(resposta.getBody()).isNull();
    }

    @Test
    @DisplayName("Deve listar todos os livros com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveListarTodosLivrosComSucesso() {
        ResponseEntity<PageResponse<LivroResponse>> resposta =
                template.exchange(
                        "/livros",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<LivroResponse>>() {
                        }
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().content().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve buscar um livro pelo id com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLivroPeloIdComSucesso() {

        ResponseEntity<LivroResponse> resposta =
                template.exchange(
                        "/livros/{" + 1L + "}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LivroResponse>() {
                        },
                        1L
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().titulo()).isNotNull();
        assertThat(resposta.getBody().isbn()).isNotNull();
        assertThat(resposta.getBody().categoria()).isNotNull();
        assertThat(resposta.getBody().statusLivro()).isNotNull();

    }

    @Test
    @DisplayName("Deve buscar um livro pelo titulo com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLivroPeloTituloComSucesso() {
        String titulo = "O Senhor dos Anéis: A Sociedade do Anel";

        ResponseEntity<LivroResponse> resposta =
                template.exchange(
                        "/livros/titulo?titulo={titulo}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<LivroResponse>() {
                        },
                        titulo
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().titulo()).isEqualTo(titulo);
        assertThat(resposta.getBody().isbn()).isNotNull();
        assertThat(resposta.getBody().dataDePublicacao()).isNotNull();
        assertThat(resposta.getBody().categoria()).isNotNull();
        assertThat(resposta.getBody().statusLivro()).isEqualTo(StatusLivro.DISPONIVEL);
    }

    @Test
    @DisplayName("Deve buscar um livro pela Categoria com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLivroPelaCategoriaComSucesso() {
        CategoriaDeLivro categoria = CategoriaDeLivro.ROMANCE;

        ResponseEntity<PageResponse<LivroResponse>> resposta =
                template.exchange(
                        "/livros/categoria?categoria={categoria}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<LivroResponse>>() {
                        },
                        categoria.name()
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();

        assertThat(resposta.getBody().content().get(0).id()).isNotNull();
        assertThat(resposta.getBody().content().get(0).titulo()).isNotNull();
        assertThat(resposta.getBody().content().get(0).isbn()).isNotNull();
        assertThat(resposta.getBody().content().get(0).dataDePublicacao()).isNotNull();
        assertThat(resposta.getBody().content().get(0).categoria()).isNotNull();
        assertThat(resposta.getBody().content().get(0).statusLivro()).isEqualTo(StatusLivro.DISPONIVEL);
    }


    @Test
    @DisplayName("Deve buscar um livros pelo id do autor com sucesso!")
    @Sql(scripts = {"/gera_autor_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/gera_livros_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/limpa_banco.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deveBuscarUmLivrosPeloIdDoAutorComSucesso() {
        Long idAutor = 1L;

        ResponseEntity<PageResponse<LivroResponse>> resposta =
                template.exchange(
                        "/livros/id/autor?id={id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<LivroResponse>>() {
                        },
                        idAutor
                );
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().content().get(0).id()).isEqualTo(idAutor);
        assertThat(resposta.getBody().content().get(0).titulo()).isNotNull();
        assertThat(resposta.getBody().content().get(0).isbn()).isNotNull();
        assertThat(resposta.getBody().content().get(0).dataDePublicacao()).isNotNull();
        assertThat(resposta.getBody().content().get(0).categoria()).isNotNull();
    }
}