package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusLivro;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.IsbnJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivroAlugadoException;
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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LivroServiceImpl implements LivroServiceI {

    private final LivroRepository repository;
    private static final LivroMapper mapper = LivroMapper.INSTANCE;
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
        Livro livro = buscar(id);
       for(Aluguel aluguel: livro.getAlugueis()){
           if(aluguel.getStatus()== StatusAluguel.ATIVO) {
               throw new LivroAlugadoException("Não foi possível remover o livro, pois ele está" +
                       " alugado no aluguel de ID: #" + aluguel.getId() + ".");
           }
       }
       livro.setAtivo(false);
       repository.save(livro);
    }

    @Override
    public Page<LivroResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(mapper::toResponse);
    }

    @Override
    public LivroResponse buscarPorId(Long id) {
        Livro livro = buscar(id);
        return mapper.toResponse(livro);
    }

    @Override
    public LivroResponse buscarPorTitulo(String titulo) {
        Livro livro = repository.findByTituloIgnoreCaseAndAtivoTrue(titulo).orElseThrow(
                () -> new LivroNaoEncontradoException(
                        "Não foi encontrado nenhum livro para o titulo {" + titulo + "}"
                ));
        return mapper.toResponse(livro);
    }

    @Override
    public Page<LivroResponse> buscarPorCategoria(CategoriaDeLivro categoria, Pageable pageable) {
        return repository.findByCategoriaAndAtivoTrue(categoria, pageable).map(mapper::toResponse);
    }

    @Override
    public Page<LivroResponse> buscarPorIdDoAutor(Long id, Pageable pageable) {
        return repository.findByAutores_IdAndAtivoTrue(id, pageable).map(mapper::toResponse);
    }

    @Override
    public Livro adicionaAutores(List<Long> idAutores, Livro livro) {
        List<Autor> autores = new ArrayList<>();
        for (Long id : idAutores) {
            livro.adicionarAutor(autorServiceI.buscar(id));
        }
        return livro;
    }

    @Override
    public Livro buscar(Long id) {
        Optional<Livro> livro = repository.findById(id);
        if(livro.isEmpty() || livro.get().getAtivo() == false){
            throw new LivroNaoEncontradoException(
                    "Não foi encontrado nenhum livro para o ID {" + id + "}"
            );
        }
     return livro.get();
    }


    public void validaIsbn(String isbn) {
        repository.findLivroByIsbnAndAtivoTrue(isbn)
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