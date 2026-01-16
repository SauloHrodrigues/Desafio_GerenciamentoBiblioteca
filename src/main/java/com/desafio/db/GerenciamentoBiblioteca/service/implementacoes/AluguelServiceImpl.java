package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.Status;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivroIndisponivelException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LivrosIndisponiveisException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.AluguelMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.AluguelRepository;
import com.desafio.db.GerenciamentoBiblioteca.service.AluguelServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AluguelServiceImpl implements AluguelServiceI {
    private final AluguelRepository repository;
    private final LivroServiceImpl  livroService;
    private final LocatarioServiceImpl locatarioService;

    private static final AluguelMapper mapper = AluguelMapper.INSTANCE;

    @Override
    public AluguelResponse cadastrar(AluguelRequest dto) {
        Aluguel aluguel = mapper.toEntity(dto);
        aluguel = adicionaLocatario(dto,aluguel);
        aluguel = adicionaLivros(dto,aluguel);
        aluguel = repository.save(aluguel);
        return mapper.toResponse(aluguel);
    }

    @Override
    public Page<AluguelResponse>  listarLivros(Long id) {
        return null;
    }

    @Override
    public Page<AluguelResponse> listarAlugueis(Pageable pageable) {
        return null;
    }

    @Override
    public AluguelResponse buscarPorId(Long id) {
        return null;
    }

    protected Aluguel adicionaLocatario(AluguelRequest dto, Aluguel aluguel){
        aluguel.setLocatario(locatarioService.buscar(dto.idLocatario()));
        return aluguel;
    }

    protected Aluguel adicionaLivros(AluguelRequest dto, Aluguel aluguel) {

        List<String> livrosIndisponiveis = new ArrayList<>();

        for (Long id : dto.livrosIds()) {
            Livro livro = livroService.buscar(id);

            if (livro.getStatus() == Status.ALUGADO) {
                livrosIndisponiveis.add(livro.getTitulo());
                continue;
            }

            livro.setStatus(Status.ALUGADO);
            aluguel.adicionarLivro(livro);
        }

        if (!livrosIndisponiveis.isEmpty()) {
            throw new LivrosIndisponiveisException(livrosIndisponiveis);
        }

        return aluguel;
    }

}
