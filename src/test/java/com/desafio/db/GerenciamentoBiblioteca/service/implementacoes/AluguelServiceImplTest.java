package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.AluguelNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivroIndisponivelException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivrosIndisponiveisException;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.AluguelFixture;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.LivroFixture;
import com.desafio.db.GerenciamentoBiblioteca.repository.AluguelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AluguelServiceImplTest {

    @InjectMocks
    private AluguelServiceImpl service;

    @Mock
    private AluguelRepository repository;

    @Mock
    private LocatarioServiceImpl locatarioService;

    @Mock
    private LivroServiceImpl livroService;

    private Aluguel aluguel;
    private Aluguel aluguelSemData;
    private AluguelRequest request;
    private AluguelRequest requestSemData;

    @BeforeEach
    void setUp(){
        aluguel = AluguelFixture.toEntity();
        aluguelSemData = AluguelFixture.toEntityParaSemData();
        request = AluguelFixture.request();
        requestSemData = AluguelFixture.requestSemDatas();
    }


    @Test
    @DisplayName("Deve cadastrar um novo aluguel com sucesso.")
    void cadastrar() {
        Locatario locatario = aluguel.getLocatario();
        Long idLivro01 = request.livrosIds().get(0);
        Livro livro01 = LivroFixture.entity();

        when(locatarioService.buscar(request.idLocatario())).thenReturn(locatario);
        when(livroService.buscar(idLivro01)).thenReturn(livro01);
        when(repository.save(any(Aluguel.class))).thenReturn(aluguel);

        AluguelResponse resposta = service.cadastrar(request);

       assertNotNull(resposta);
       assertEquals(request.idLocatario(),resposta.locatario().id());
       assertEquals(request.idLocatario(), resposta.locatario().id());
       assertEquals(request.retirada(), resposta.retirada());
       assertEquals(request.devolucao(), resposta.devolucao());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar cadastrar um novo aluguel com livro indisponível.")
    void deveLancarExcessaoAoTentarCadastrarAluguelComLivroIndisponivel() {
        Locatario locatario = aluguel.getLocatario();
        Long idLivro01 = request.livrosIds().get(0);
        Livro livro01 = LivroFixture.entity();
        livro01.setStatusLivro(StatusLivro.ALUGADO);

        when(locatarioService.buscar(request.idLocatario())).thenReturn(locatario);
        when(livroService.buscar(idLivro01)).thenReturn(livro01);

        LivrosIndisponiveisException ressposta = assertThrows(LivrosIndisponiveisException.class,()->{
           service.cadastrar(request);
        });

        assertTrue(ressposta.getTitulos().contains(livro01.getTitulo()));
    }

    @Test
    @DisplayName("Deve listar os livros de determinado aluguel.")
    void listarLivros() {
        Long id = aluguel.getId();
        List<Livro> livros = aluguel.getLivros();
        Pageable pageable = PageRequest.of(0,15);

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));

        Page<LivroResponse> resposta = service.listarLivros(id,pageable);

        assertNotNull(resposta);
        assertEquals(aluguel.getLivros().size(),resposta.getContent().size());
        assertEquals(aluguel.getLivros().get(0).getTitulo(),resposta.getContent().get(0).titulo());
    }

    @Test
    @DisplayName("Deve retornar todos os alugueis cadastrados")
    void listarAlugueis() {
        List<Aluguel> alugueis = List.of(aluguel,aluguel);
        Pageable pageable = PageRequest.of(0,15);
        Page<Aluguel> pageAlugueis = new PageImpl<>(alugueis,pageable, alugueis.size());

        when(repository.findAll(pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponse> resposta = service.listarAlugueis(pageable);

        assertNotNull(resposta);
        assertEquals(alugueis.size(),resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve listar os alugueis inativos.")
    void testListarAlugueisInativos() {
        Aluguel aluguelAi = AluguelFixture.toEntity();
        aluguelAi.setStatus(StatusAluguel.INATIVO);
        List<Aluguel> alugueis = List.of(aluguelAi);
        Pageable pageable = PageRequest.of(0,15);
        Page<Aluguel> pageAlugueis = new PageImpl<>(alugueis,pageable, alugueis.size());

        when(repository.findAluguelsByStatus(StatusAluguel.INATIVO,pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponse> resposta = service.listarAlugueis("inativo",pageable);

        assertNotNull(resposta);
        assertEquals(alugueis.size(),resposta.getContent().size());
        assertEquals(StatusAluguel.INATIVO, resposta.getContent().get(0).status());
        verify(repository).findAluguelsByStatus(StatusAluguel.INATIVO,pageable);
    }

    @Test
    @DisplayName("Deve listar os alugueis Ativos.")
    void testListarAlugueisAtivos() {
        Aluguel alugueisAtivos = AluguelFixture.toEntity();
        List<Aluguel> alugueis = List.of(alugueisAtivos);
        Pageable pageable = PageRequest.of(0,15);
        Page<Aluguel> pageAlugueis = new PageImpl<>(alugueis,pageable, alugueis.size());

        when(repository.findAluguelsByStatus(StatusAluguel.ATIVO,pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponse> resposta = service.listarAlugueis("ativos",pageable);

        assertNotNull(resposta);
        assertEquals(alugueis.size(),resposta.getContent().size());
        assertEquals(StatusAluguel.ATIVO, resposta.getContent().get(0).status());
        verify(repository).findAluguelsByStatus(StatusAluguel.ATIVO,pageable);
    }

    @Test
    @DisplayName("Deve retornar um aluguel response buscado por id.")
    void deveRetornaUmAluguelResponseBuscadoPorId() {
        Long id = aluguel.getId();

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));

        AluguelResponse resposta = service.buscarPorId(id);

        assertNotNull(resposta);
        assertEquals(request.idLocatario(),resposta.locatario().id());
        assertEquals(request.idLocatario(), resposta.locatario().id());
        assertEquals(request.retirada(), resposta.retirada());
        assertEquals(request.devolucao(), resposta.devolucao());
    }

    @Test
    @DisplayName("Deve devolver um aluguel existente")
    void devolverAluguel() {
        Long id = aluguel.getId();
       aluguel.getLivros().get(0).setStatusLivro(StatusLivro.DISPONIVEL);

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));
        service.devolverAluguel(id);

        assertEquals(StatusAluguel.INATIVO,aluguel.getStatus());
        assertEquals(StatusLivro.DISPONIVEL, aluguel.getLivros().get(0).getStatusLivro());

        verify(livroService).devolveLivro(aluguel.getLivros().get(0));
        verify(repository).save(aluguel);
    }

    @Test
    @DisplayName("Deve buscar um aluguel pelo id e retornar um aluguel entity. uso interno")
    void deveBuscarUmAluguelPeloId() {
        Long id = aluguel.getId();

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));

        Aluguel resposta = service.buscar(id);

        assertNotNull(resposta);
        assertEquals(request.idLocatario(),resposta.getLocatario().getId());
        assertEquals(request.idLocatario(), resposta.getLocatario().getId());
        assertEquals(request.retirada(), resposta.getRetirada());
        assertEquals(request.devolucao(), resposta.getDevolucao());

    }

    @Test
    @DisplayName("Deve lancar excessao ao buscar um aluguel pelo id e retornar um aluguel entity. uso interno")
    void deveLancarExcessaoAoBuscarUmAluguelPeloId(){
        Long id = aluguel.getId();

        when(repository.findById(id)).thenReturn(Optional.empty());

        AluguelNaoEncontradoException resposta =  assertThrows(AluguelNaoEncontradoException.class,()->{
            service.buscar(id);
        });

        assertEquals("Não foi localizado um aluguel como ID: #" +
                id+".",resposta.getMessage());
    }

    @Test
    @DisplayName("Deve adicionar um livro ao aluguel.")
    void deveAdicionaLivros() {
        Livro livro = LivroFixture.entity();
        Aluguel aluguel = AluguelFixture.toEntitySemLivro();

        when(livroService.buscar(livro.getId())).thenReturn(livro);

        Aluguel resposta = service.adicionaLivros(request,aluguel);

        assertNotNull(resposta);
        assertNotNull(resposta.getLivros().get(0).getAutores());
        assertEquals(livro.getTitulo(),resposta.getLivros().get(0).getTitulo());
        assertEquals(livro.getIsbn(),resposta.getLivros().get(0).getIsbn());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar adicionar um livro já alugado.")
    void deveLancarExcessaoAoAdicionaLivrosJaAlugados() {
        Livro livro = LivroFixture.entity();
        livro.setStatusLivro(StatusLivro.ALUGADO);
        Aluguel aluguel = AluguelFixture.toEntitySemLivro();

        when(livroService.buscar(livro.getId())).thenReturn(livro);

        LivrosIndisponiveisException excessao = assertThrows(LivrosIndisponiveisException.class, ()->{
            service.adicionaLivros(request,aluguel);
        });

        assertTrue(excessao.getTitulos().contains(livro.getTitulo()));
        verify(livroService).buscar(livro.getId());
    }
}