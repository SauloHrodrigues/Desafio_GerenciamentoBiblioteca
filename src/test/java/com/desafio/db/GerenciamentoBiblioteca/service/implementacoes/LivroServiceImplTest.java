package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.Status;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.IsbnJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivroNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LocatarioNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.LivroFixture;
import com.desafio.db.GerenciamentoBiblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LivroServiceImplTest {

    @InjectMocks
    private LivroServiceImpl service;

    @Mock
    private LivroRepository repository;

    @Mock
    private AutorServiceImpl autorService;

    private LivroRequest livroRequest;

    private Livro livro;

    @BeforeEach
    void setUp() {
        livroRequest = LivroFixture.reguest();
        livro = LivroFixture.entity();
    }

    @Test
    @DisplayName("Deve cadastrar um novo livro com sucesso.")
    void deveCadastrarUmLivro() {
        String isbn = this.livroRequest.isbn();
        Long idAutor01 = this.livroRequest.idAutores().get(0);
        Long idAutor02 = this.livroRequest.idAutores().get(1);

        when(autorService.buscar(idAutor01)).thenReturn(this.livro.getAutores().get(0));
        when(autorService.buscar(idAutor02)).thenReturn(this.livro.getAutores().get(1));
        when(repository.findLivroByIsbn(isbn)).thenReturn(Optional.empty());
        when(repository.save(any(Livro.class))).thenReturn(this.livro);

        LivroResponse resposta = service.cadastrar(this.livroRequest);

        assertNotNull(resposta.id());
        assertEquals(this.livroRequest.titulo().toLowerCase(),resposta.titulo());
        assertEquals(this.livroRequest.isbn(),resposta.isbn());
        assertEquals(this.livroRequest.dataDePublicacao(),resposta.dataDePublicacao());
        assertEquals(Status.DISPONIVEL,resposta.status());
    }

    @Test
    @DisplayName("Deve atualizar um livro encontrado pelo Id.")
    void deveAtualizarUmLivro() {
        Long id = this.livro.getId();
        LivroAtualiza atualizacoes = new LivroAtualiza("O Segredo","1234567899874",null, CategoriaDeLivro.AUTOAJUDA);

        when(repository.findById(id)).thenReturn(Optional.of(this.livro));

        LivroResponse resposta = service.atualizar(id,atualizacoes);

        assertNotNull(resposta.id());
        assertEquals(atualizacoes.titulo(),resposta.titulo());
        assertEquals(atualizacoes.isbn(),resposta.isbn());
        assertEquals(this.livroRequest.dataDePublicacao(),resposta.dataDePublicacao());
        assertEquals(atualizacoes.categoria(),resposta.categoria());
    }

    @Test
    @DisplayName("Deve apagar um livro encontrado pelo id com sucesso.")
    void deveExcuirUmLivro() {
        //TODO
    }

    @Test
    @DisplayName("Deve listar todos os LIVROS cadastrados com sucesso.")
    void deveListarTodosLocatarios() {
        List<Livro> livros = List.of(this.livro, this.livro);
        Pageable pageable = PageRequest.of(0, 15);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(repository.findAll(pageable)).thenReturn(pageLivros);

        Page<LivroResponse> resposta = service.listarTodos(pageable);

        assertNotNull(resposta);
        assertEquals(livros.size(), resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve buscar um livro pelo id com sucesso.")
    void deveBuscarUmLivroPeloId() {
        Long id = this.livro.getId();

        when(repository.findById(id)).thenReturn(Optional.of(this.livro));

        LivroResponse resposta = service.buscarPorId(id);

        assertNotNull(resposta.id());
        assertEquals(this.livro.getTitulo(), resposta.titulo());
        assertEquals(this.livro.getIsbn(), resposta.isbn());
        assertEquals(this.livro.getDataDePublicacao(), resposta.dataDePublicacao());
    }



    @Test
    @DisplayName("Deve buscar um livro pelo titulo com sucesso.")
    void deveBuscarUmLivroPeloTitulo() {
        String titulo = this.livroRequest.titulo();

        when(repository.findByTituloIgnoreCase(titulo)).thenReturn(Optional.of(this.livro));

        LivroResponse resposta = service.buscarPorTitulo(titulo);

        assertNotNull(resposta.id());
        assertEquals(this.livro.getTitulo(), resposta.titulo());
        assertEquals(this.livro.getIsbn(), resposta.isbn());
        assertEquals(this.livro.getDataDePublicacao(), resposta.dataDePublicacao());
    }


    @Test
    @DisplayName("Deve lançar excessão ao buscar um livro pelo titulo.")
    void deveLancarExcessaoBuscarUmLivroPeloTitulo()  {
        String titulo="Qualquer";

        when(repository.findByTituloIgnoreCase(titulo)).thenReturn(Optional.empty());

        LivroNaoEncontradoException resposta = assertThrows(LivroNaoEncontradoException.class,
                ()->{service.buscarPorTitulo(titulo);});

        assertEquals(
                "Não foi encontrado nenhum livro para o titulo {" + titulo + "}", resposta.getMessage());

        verify(repository).findByTituloIgnoreCase(titulo);
    }

    @Test
    @DisplayName("Deve buscar todos os livros de determinado autor.")
    void deveBuscarLivrosPorIdDoAutor() {
        Long idAutor = livro.getAutores().get(0).getId();
        List<Livro> livros = List.of(this.livro, this.livro);
        Pageable pageable = PageRequest.of(0, 15);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(repository.findByAutores_Id(idAutor,pageable)).thenReturn(pageLivros);

        Page<LivroResponse> resposta = service.buscarPorIdDoAutor(idAutor,pageable);

        assertNotNull(resposta);
        assertEquals(livros.size(), resposta.getContent().size());
        verify(repository).findByAutores_Id(idAutor,pageable);
    }

    @Test
    @DisplayName("Deve adicionar autores a um livro")
    void adicionaAutores() {
        Autor autor01 = this.livro.getAutores().get(0);
        Autor autor02 = this.livro.getAutores().get(1);
        List<Long> listIds = List.of(autor01.getId(),autor02.getId());

        when(autorService.buscar(autor01.getId())).thenReturn(autor01);
        when(autorService.buscar(autor02.getId())).thenReturn(autor02);

        Livro resposta = service.adicionaAutores(listIds,this.livro);

        assertNotNull(resposta);
        assertEquals(this.livro.getAutores().size(), resposta.getAutores().size());
    }

    @Test
    @DisplayName("Deve buscar um livro pelo id, trazendo como retorno uma entidade para uso interno.")
    void buscar() {
        Long id = this.livro.getId();

        when(repository.findById(id)).thenReturn(Optional.of(this.livro));

        Livro resposta = service.buscar(id);

        assertNotNull(resposta);
        assertNotNull(resposta.getId());
    }

    @Test
    @DisplayName("Deve lançar excessão ao buscar um livro não cadastrado, pelo id.")
    void deverLancarExcessaoAoBuscarPorIdNaoCadastrado() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        LivroNaoEncontradoException resposta = assertThrows(LivroNaoEncontradoException.class,
                ()->{service.buscar(id);});

        assertEquals("Não foi encontrado nenhum livro para o ID {" + id + "}", resposta.getMessage());

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve validar isbn não cadastrado.")
    void deveValidaIsbn() {
        String isbn = livro.getIsbn();

        when(repository.findLivroByIsbn(isbn)).thenReturn(Optional.empty());

        service.validaIsbn(isbn);

        verify(repository).findLivroByIsbn(isbn);
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar validar isbn já cadastrado.")
    void deveLancarExcessoValidaIsbn() {
        String isbn = livro.getIsbn();

        when(repository.findLivroByIsbn(isbn)).thenReturn(Optional.of(this.livro));

        IsbnJaExistenteException resposta = assertThrows(IsbnJaExistenteException.class,()->{
            service.validaIsbn(isbn);
        });

        assertEquals( "O ISBN {" + isbn + "} já consta em nosso banco.",resposta.getMessage());

    }
}