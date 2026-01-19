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
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivrosIndisponiveisException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.AluguelMapper;
import com.desafio.db.GerenciamentoBiblioteca.mappers.LivroMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.AluguelRepository;
import com.desafio.db.GerenciamentoBiblioteca.service.AluguelServiceI;
import com.desafio.db.GerenciamentoBiblioteca.service.LivroServiceI;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AluguelServiceImpl implements AluguelServiceI {
    private final AluguelRepository repository;
    private final LivroServiceI livroServiceI;
    private final LocatarioServiceI locatarioServiceI;

    private static final AluguelMapper MAPPER = AluguelMapper.INSTANCE;
    private static final LivroMapper LIVRO_MAPPER = LivroMapper.INSTANCE;

    @Override
    public AluguelResponse cadastrar(AluguelRequest dto) {
        Locatario locatario = locatarioServiceI.buscar(dto.idLocatario());
        Aluguel aluguel = MAPPER.toEntity(dto);
        aluguel.setLocatario(locatario);
        aluguel = adicionaLivros(dto,aluguel);
        aluguel= repository.save(aluguel);
        return MAPPER.toResponse(aluguel);
    }

    @Override
    public Page<LivroResponse>  listarLivros(Long id, Pageable pageable) {
        Aluguel aluguel = buscar(id);
       List<LivroResponse>livros = LIVRO_MAPPER.toResponse(aluguel.getLivros());
        return new PageImpl<>(livros,pageable, livros.size());
    }

    @Override
    public Page<AluguelResponse> listarAlugueis(Pageable pageable) {
        return repository.findAll(pageable).map(MAPPER::toResponse);
    }

    @Override
    public Page<AluguelResponse> listarAlugueis(String status,Pageable pageable){
        if(status.equalsIgnoreCase(StatusAluguel.INATIVO.name())){
            return repository.findAluguelsByStatus(StatusAluguel.INATIVO,pageable).map(MAPPER::toResponse);
        } else {
            return repository.findAluguelsByStatus(StatusAluguel.ATIVO,pageable).map(MAPPER::toResponse);
        }
    }

    @Override
    public AluguelResponse buscarPorId(Long id) {
        Aluguel aluguel = buscar(id);
        return MAPPER.toResponse(aluguel);
    }

    @Override
    public void devolverAluguel(Long id){
        Aluguel aluguel = buscar(id);
        aluguel.setStatus(StatusAluguel.INATIVO);
        for (Livro livro: aluguel.getLivros()){
            livroServiceI.devolveLivro(livro);
        }
        repository.save(aluguel);
    }

    protected Aluguel buscar(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new AluguelNaoEncontradoException("Não foi localizado um aluguel com" +
                        "o ID: #"+id+".")
        );
    }

    protected Aluguel adicionaLivros(AluguelRequest dto, Aluguel aluguel) {

        List<String> livrosIndisponiveis = new ArrayList<>();

        for (Long id : dto.livrosIds()) {
            Livro livro = livroServiceI.buscar(id);

            if (livro.getStatusLivro() == StatusLivro.ALUGADO) {
                livrosIndisponiveis.add(livro.getTitulo());//erro
                continue;
            }

            livro.setStatusLivro(StatusLivro.ALUGADO);
            aluguel.adicionarLivro(livro);
        }

        if (!livrosIndisponiveis.isEmpty()) {
            throw new LivrosIndisponiveisException(livrosIndisponiveis);
        }

        return aluguel;
    }

}
