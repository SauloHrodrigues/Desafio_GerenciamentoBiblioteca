package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.CpfJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LocatarioNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.fixtures.LocatarioFixture;
import com.desafio.db.GerenciamentoBiblioteca.repository.LocatarioRepositiry;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocatarioServiceImplTest {

    @InjectMocks
    private LocatarioServiceImpl service;

    @Mock
    private LocatarioRepositiry repository;

    private LocatarioRequest locatarioRequest;
    private Locatario locatario;

    @BeforeEach
    void setUp() {
        locatarioRequest = LocatarioFixture.toRequest();
        locatario = LocatarioFixture.toEmtity();
    }

    @Test
    @DisplayName("Deve cadastrar um novo locatario com sucesso.")
    void cadastrar() {
        String cpf = locatarioRequest.cpf().replaceFirst(
                "(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4"
        );

        when(repository.findByCpf(cpf)).thenReturn(Optional.empty());
        when(repository.save(any(Locatario.class))).thenReturn(locatario);

        LocatarioResponse resposta = service.cadastrar(locatarioRequest);

        assertNotNull(resposta.id());
        assertEquals(locatarioRequest.nome(),resposta.nome());
        assertEquals(cpf,resposta.cpf());
        assertEquals(locatarioRequest.sexo(),resposta.sexo());
        assertEquals(locatarioRequest.telefone(),resposta.telefone());
        assertEquals(locatarioRequest.email(),resposta.email());
        assertEquals(locatarioRequest.dataDeNascimento(),resposta.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve atualizar um locataio encontrado pelo Id.")
    void deveAtualizarUmLocatarioEncontradopeloId() {
        Long id = locatario.getId();
        LocatarioAtualiza atualizacoes = new LocatarioAtualiza("Novo Nome",null,"11111111111",null, LocalDate.of(2005,5,23));

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        LocatarioResponse resposta = service.atualizar(id,atualizacoes);

        assertNotNull(resposta.id());
        assertEquals(atualizacoes.nome().toLowerCase(),resposta.nome());
        assertEquals(locatarioRequest.cpf(),resposta.cpf());
        assertEquals(locatarioRequest.sexo(),resposta.sexo());
        assertEquals(atualizacoes.telefone(),resposta.telefone());
        assertEquals(locatarioRequest.email(),resposta.email());
        assertEquals(atualizacoes.dataDeNascimento(),resposta.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve apagar um locatario encontrado pelo id com sucesso.")
    void deveExcuirUmLocatario() {
        Long id = locatario.getId();

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        service.apagar(id);

        verify(repository).delete(locatario);
    }

    @Test
    @DisplayName("Deve listar todos os autores cadastrados com sucesso.")
    void deveListarTodosLocatarios() {
        List<Locatario> locatarios = List.of(locatario,locatario);
        Pageable pageable = PageRequest.of(0,15);
        Page<Locatario> pageLocatario = new PageImpl<>(locatarios,pageable,locatarios.size());

        when(repository.findAll(pageable)).thenReturn(pageLocatario);

        Page<LocatarioResponse> resposta = service.listarTodos(pageable);

        assertNotNull(resposta);
        assertEquals(locatarios.size(), resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve buscar um locatario pelo id com sucesso.")
    void deveBuscarUmLocatarioPeloId() {
        Long id = locatario.getId();

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        LocatarioResponse resposta = service.buscarPorId(id);

        assertNotNull(resposta);
        assertNotNull(resposta.id());
        assertEquals(locatario.getNome(), resposta.nome());
        assertEquals(locatario.getCpf(), resposta.cpf());
        assertEquals(locatario.getSexo(), resposta.sexo());
        assertEquals(locatario.getDataDeNascimento(), resposta.dataDeNascimento());
    }

    @Test //TODO
    void listarTodosAlugueis() {
    }

    @Test
    @DisplayName("Deve buscar um locatario pelo id, trazendo como retorno uma entidade para uso interno.")
    void buscar() {
        Long id = locatario.getId();

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        Locatario resposta = service.buscar(id);

        assertNotNull(resposta);
        assertNotNull(resposta.getId());
        assertEquals(locatario.getNome(), resposta.getNome());
        assertEquals(locatario.getCpf(), resposta.getCpf());
        assertEquals(locatario.getSexo(), resposta.getSexo());
        assertEquals(locatario.getDataDeNascimento(), resposta.getDataDeNascimento());
    }


    @Test
    @DisplayName("Deve lançar excessão ao buscar um locatario pelo id, não cadastrado.")
    void deverLancarExcessaoAoBuscarPorIdNaoCadastrado() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        LocatarioNaoEncontradoException resposta = assertThrows(LocatarioNaoEncontradoException.class,
                ()->{service.buscar(id);});

        assertEquals("Não foi possível localizar nenhum" +
                        " locatário para o ID: #'{"+id+"}'", resposta.getMessage());

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve validar um cpf não cadastrado no banco.")
    void deveValidarCpfNaoCadastrado() {
        String cpf = locatario.getCpf();

        when(repository.findByCpf(cpf)).thenReturn(Optional.empty());
            service.validarCpf(cpf);
        verify(repository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve lançar excessao de já não cadastrado no banco.")
    void deveLancarExcessaoCpfCadastrado() {
        String cpf = locatario.getCpf();

        when(repository.findByCpf(cpf)).thenReturn(Optional.of(locatario));

        CpfJaExistenteException resposta= assertThrows(CpfJaExistenteException.class, ()->{
            service.validarCpf(cpf);
        });
        assertEquals("Já existe locatário registrado para o CPF '{" + cpf+"'}", resposta.getMessage());
        verify(repository).findByCpf(cpf);
    }
}