package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.IsbnJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivroNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.LivroMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.LivroRepository;
import com.desafio.db.GerenciamentoBiblioteca.service.AutorServiceI;
import com.desafio.db.GerenciamentoBiblioteca.service.LivroServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LivroServiceImpl implements LivroServiceI {

    private final LivroRepository repository;
    private static final LivroMapper mapper = LivroMapper.INSTANCE;
    private final AutorServiceImpl autorService;
    private final AutorServiceI autorServiceI;

    @Override
    public LivroResponse cadastrar(LivroRequest dto) {
        validaIsbn(dto.isbn());
        Livro livro = mapper.toEntity(dto);
        livro = adicionaAutores(dto.idAutores(), livro);
        livro = repository.save(livro);
        return mapper.toResponse(livro);
    }

    @Override
    public LivroResponse atualizar(Long id, LivroAtualiza atualizacoes) {
        Livro livro = buscar(id);
        mapper.toUpdate(atualizacoes, livro);
        livro = repository.save(livro);
        return mapper.toResponse(livro);
    }

    @Override
    public void apagar(Long id) {

    }

    @Override
    public Page<LivroResponse> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public LivroResponse buscarPorId(Long id) {
        Livro livro = buscar(id);
        return mapper.toResponse(livro);
    }

    @Override
    public LivroResponse buscarPorTitulo(String titulo) {
        Livro livro = repository.findByTituloIgnoreCase(titulo).orElseThrow(
                () -> new LivroNaoEncontradoException(
                        "Não foi encontrado nenhum livro para o titulo {" + titulo + "}"
                ));
        return mapper.toResponse(livro);
    }

    @Override
    public Page<LivroResponse> buscarPorCategoria(CategoriaDeLivro categoria, Pageable pageable) {
        return repository.findByCategoria(categoria, pageable).map(mapper::toResponse);
    }

    @Override
    public Page<LivroResponse> buscarPorIdDoAutor(Long id, Pageable pageable) {
        return repository.findByAutores_Id(id, pageable).map(mapper::toResponse);
    }

    @Override
    public Livro adicionaAutores(List<Long> idAutores, Livro livro) {
        List<Autor> autores = new ArrayList<>();
        for (Long id : idAutores) {
            livro.adicionarAutor(autorService.buscar(id));
        }
        return livro;
    }

    @Override
    public Livro buscar(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new LivroNaoEncontradoException(
                        "Não foi encontrado nenhum livro para o ID {" + id + "}"
                )
        );
    }

    @Override
    public void validaIsbn(String isbn) {
        repository.findLivroByIsbn(isbn)
                .ifPresent(livro -> {
                    throw new IsbnJaExistenteException(
                            "O ISBN {" + isbn + "} já consta em nosso banco."
                    );
                });
    }

    @Override
    public void devolveLivro(Livro livro){
        livro.setStatusLivro(StatusLivro.DISPONIVEL);
        repository.save(livro);
    }
}