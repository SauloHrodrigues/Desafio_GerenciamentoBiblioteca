package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.enun.Sexo;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.AutorNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.AutorFixtures;
import com.desafio.db.GerenciamentoBiblioteca.repository.AutorRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {

    @InjectMocks
    private AutorServiceImpl service;

    @Mock
    private AutorRepository repository;

    @Test
    @DisplayName("Deve cadastrar um novo autor com sucesso.")
    void cadastrar() {
        AutorRequest dto = AutorFixtures.request();
        Autor autor = AutorFixtures.entity();

        when(repository.save(any(Autor.class))).thenReturn(autor);

        AutorResponse resposta = service.cadastrar(dto);
        System.out.println(resposta.nome());
        assertNotNull(resposta);
        assertNotNull(resposta.id());
        assertEquals(dto.nome().toLowerCase(), resposta.nome());
        assertEquals(dto.cpf(), resposta.cpf());
        assertEquals(dto.sexo(), resposta.sexo());
        assertEquals(dto.anoDeNascimento(), resposta.anoDeNascimento());
    }

    @Test
    @DisplayName("Deve atualizar um autor encontrado pelo Id.")
    void deveAtualizarUmAutorEncontradopeloId() {
        Autor autor = AutorFixtures.entity();
        Long id = autor.getId();
        AutorAtualiza atualizacoes = new AutorAtualiza("maria", Sexo.FEMININO,null,2003);

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        AutorResponse resposta = service.atualizar(id,atualizacoes);

        assertNotNull(resposta);
        assertNotNull(resposta.id());
        assertEquals(atualizacoes.nome(), resposta.nome());
        assertEquals(autor.getCpf(), resposta.cpf());
        assertEquals(atualizacoes.sexo(), resposta.sexo());
        assertEquals(atualizacoes.anoDeNascimento(), resposta.anoDeNascimento());

    }

    @Test
    @DisplayName("Deve apagar um autor encontrado pelo id com sucesso.")
    void deveExcuirUmAutor() {
        Autor autor = AutorFixtures.entity();
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        service.apagar(id);

        verify(repository).delete(autor);
    }

    @Test
    @DisplayName("Deve buscar um autor pelo nome com sucesso.")
    void deveBuscarUmAutorPeloNome() {
        Autor autor = AutorFixtures.entity();
        String nome = autor.getNome();

        when(repository.findAutorByNome(nome)).thenReturn(Optional.of(autor));

        AutorResponse resposta = service.buscarPorNome(nome);

        assertNotNull(resposta);
        assertNotNull(resposta.id());
        assertEquals(autor.getNome(), resposta.nome());
        assertEquals(autor.getCpf(), resposta.cpf());
        assertEquals(autor.getSexo(), resposta.sexo());
        assertEquals(autor.getAnoDeNascimento(), resposta.anoDeNascimento());

    }

    @Test
    @DisplayName("Deve buscar um autor pelo id com sucesso.")
    void deveBuscarUmAutorPorId() {
        Autor autor = AutorFixtures.entity();
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        AutorResponse resposta = service.buscarPorId(id);

        assertNotNull(resposta);
        assertNotNull(resposta.id());
        assertEquals(autor.getNome(), resposta.nome());
        assertEquals(autor.getCpf(), resposta.cpf());
        assertEquals(autor.getSexo(), resposta.sexo());
        assertEquals(autor.getAnoDeNascimento(), resposta.anoDeNascimento());
    }

    @Test
    @DisplayName("Deve listar todos os autores cadastrados com sucesso.")
    void deveListarTodosAutores() {
        Autor autor = AutorFixtures.entity();
        List<Autor> autores = List.of(autor,autor,autor);
        Pageable pageable = PageRequest.of(0,15);
        Page<Autor> pageAutores = new PageImpl<>(autores,pageable,autores.size());

        when(repository.findAll(pageable)).thenReturn(pageAutores);

        Page<AutorResponse> resposta = service.listarTodos(pageable);

        assertNotNull(resposta);
        assertEquals(autores.size(), resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve buscar um autor pelo id, trazendo como retorno uma entidade para uso interno.")
    void deverBuscarPorIdRetornandoUmaEntidadeAutor() {
        Autor autor = AutorFixtures.entity();
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        Autor resposta = service.buscar(id);
        System.out.println(resposta.getNome());
        assertNotNull(resposta);
        assertNotNull(resposta.getId());
        assertEquals(autor.getNome(), resposta.getNome());
        assertEquals(autor.getCpf(), resposta.getCpf());
        assertEquals(autor.getSexo(), resposta.getSexo());
        assertEquals(autor.getAnoDeNascimento(), resposta.getAnoDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar excessão ao buscar um autor pelo id, não cadastrado.")
    void deverLancarExcessaoAoBuscarPorId() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        AutorNaoEncontradoException resposta = assertThrows(AutorNaoEncontradoException.class,
                ()->{service.buscar(id);});

        assertEquals("Não foi encontrado nenhum autor para o ID: #'{"+id+"}'",
                resposta.getMessage());
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve buscar um autor pelo nome trazendo como retorno uma entidade para uso interno.")
    void deverBuscarPeloNomeRetornandoUmaEntidadeAutor() {
        Autor autor = AutorFixtures.entity();
        String nome = autor.getNome();

        when(repository.findAutorByNome(nome)).thenReturn(Optional.of(autor));

        Autor resposta = service.buscar(nome);

        assertNotNull(resposta);
        assertNotNull(resposta.getId());
        assertEquals(autor.getNome(), resposta.getNome());
        assertEquals(autor.getCpf(), resposta.getCpf());
        assertEquals(autor.getSexo(), resposta.getSexo());
        assertEquals(autor.getAnoDeNascimento(), resposta.getAnoDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar excessão ao buscar um autor pelo nome, não cadastrado.")
    void deverLancarExcessaoAoBuscarPorNome() {
        String nome = "Nome e sobrenome";

        when(repository.findAutorByNome(nome)).thenReturn(Optional.empty());

        AutorNaoEncontradoException resposta = assertThrows(AutorNaoEncontradoException.class,
                ()->{service.buscar(nome);});

        assertEquals("Não foi encontrado nenhum autor com o nome '{"+nome+"}'",
                resposta.getMessage());
        verify(repository).findAutorByNome(nome);
    }
}