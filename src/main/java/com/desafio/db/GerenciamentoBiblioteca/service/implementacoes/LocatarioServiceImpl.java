package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LocatarioNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.CpfJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.LocatarioMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.LocatarioRepositiry;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocatarioServiceImpl implements LocatarioServiceI {
    private final LocatarioRepositiry repository;
    private static final LocatarioMapper mapper = LocatarioMapper.INSTANCE;

    @Override
    public LocatarioResponse cadastrar(LocatarioRequest dto) {
        validarCpf(dto.cpf());
        Locatario locatario = mapper.toEntity(dto);
        Locatario locatarioSalvo = repository.save(locatario);
        return mapper.toResponse(locatarioSalvo);
    }

    @Override
    public LocatarioResponse atualizar(Long id, LocatarioAtualiza atualizacoes) {
        Locatario locatario = buscar(id);
        mapper.toUpdate(atualizacoes,locatario);
        repository.save(locatario);
        return mapper.toResponse(locatario);
    }

    @Override
    public void apagar(Long id) {
        Locatario locatario= buscar(id);
        repository.delete(locatario);
    }

    @Override
    public Page<LocatarioResponse> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public LocatarioResponse buscarPorId(Long id) {
        return mapper.toResponse(buscar(id));
    }

    @Override
    public LocatarioResponse listarTodosAlugueis(Long id) {
        return mapper.toResponse(buscar(id));
    }

    protected Locatario buscar(Long id){
            return repository.findById(id).orElseThrow(
                    ()-> new LocatarioNaoEncontradoException("Não foi possível localizar nenhum" +
                            " locatário para o ID: #'{"+id+"}'")
            );
    }

    protected void validarCpf(String cpf){
        repository.findByCpf(cpf)
                .ifPresent(locatario -> {
                    throw new CpfJaExistenteException(
                            "Já existe locatário registrado para o CPF '{" + cpf+"'}"
                    );
                });
    }
}
