package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.AutorNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.AutorMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.AutorRepository;
import com.desafio.db.GerenciamentoBiblioteca.service.AutorServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AutorServiceImpl implements AutorServiceI {
    private final AutorRepository repository;
    private static final AutorMapper mapper = AutorMapper.INSTANCE;

    @Override
    public AutorResponse cadastrar(AutorRequest dto) {
        Autor autor = mapper.toEntity(dto);
        Autor autorSalvo = repository.save(autor);
        return mapper.toResponse(autorSalvo);
    }

    @Override
    public AutorResponse atualizar(Long id, AutorAtualiza atualizacoes) {
        Autor autor = buscar(id);
         mapper.toUpdate(atualizacoes, autor);
        repository.save(autor);
        return mapper.toResponse(autor);
    }

    @Override
    public void apagar(Long id) {
        Autor autor = buscar(id);
        repository.delete(autor);
    }

    @Override
    public AutorResponse buscarPorNome(String nome) {
       Autor autor = buscar(nome);
        return mapper.toResponse(autor);
    }

    @Override
    public AutorResponse buscarPorId(Long id) {
        Autor autor = buscar(id);
        return mapper.toResponse(autor);
    }

    @Override
    public Page<AutorResponse> listarTodos(Pageable pageable) {
        Page<AutorResponse> autores = repository.findAll(pageable).map(mapper::toResponse);
        return autores;
    }

    @Override
    public Autor buscar(String nome){
        return repository.findAutorByNome(nome).orElseThrow(
                ()-> new AutorNaoEncontradoException("Não foi encontrado nenhum autor com o nome '{"+nome+"}'")
        );
    }
    @Override
    public Autor buscar(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new AutorNaoEncontradoException("Não foi encontrado nenhum autor para o ID: #'{"+id+"}'")
        );
    }
}
