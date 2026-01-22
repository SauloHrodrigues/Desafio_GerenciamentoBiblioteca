package com.desafio.db.GerenciamentoBiblioteca.service.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import com.desafio.db.GerenciamentoBiblioteca.enun.StatusAluguel;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.AluguelAtivoException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.LocatarioNaoEncontradoException;
import com.desafio.db.GerenciamentoBiblioteca.exceptions.CpfJaExistenteException;
import com.desafio.db.GerenciamentoBiblioteca.mappers.LocatarioMapper;
import com.desafio.db.GerenciamentoBiblioteca.repository.LocatarioRepositiry;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        mapper.toUpdate(atualizacoes, locatario);
        repository.save(locatario);
        return mapper.toResponse(locatario);
    }

    @Override
    public void apagar(Long id) {
        Locatario locatario = buscar(id);
        validaDelecao(locatario);
        locatario.setAtivo(false);
        locatario.setNome(locatario.getNome() + "(deletado)");
        locatario.setCpf(locatario.getCpf() + "D");
        repository.save(locatario);
    }

    @Override
    public Page<LocatarioResponse> listarTodos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(mapper::toResponse);
    }

    @Override
    public LocatarioResponse buscarPorId(Long id) {
        return mapper.toResponse(buscar(id));
    }

    @Override
    public LocatarioResponse listarTodosAlugueis(Long id) {
        return mapper.toResponse(buscar(id));
    }

    public Locatario buscar(Long id) {
        Optional<Locatario> locatario = repository.findById(id);

        if(locatario.isEmpty() || locatario.get().getAtivo()==false){
            throw new LocatarioNaoEncontradoException("Não foi possível localizar nenhum" +
                    " locatário para o ID: #'{" + id + "}'");
        } else
            return locatario.get();
    }

    protected void validarCpf(String cpf) {
        repository.findByCpf(cpf)
                .ifPresent(locatario -> {
                    throw new CpfJaExistenteException(
                            "Já existe locatário registrado para o CPF '{" + cpf + "'}"
                    );
                });
    }

    protected void validaDelecao(Locatario locatario) {
        List<String> ids = new ArrayList<>();
        for (Aluguel aluguel : locatario.getAlugueis()) {
            if (aluguel.getStatus() == StatusAluguel.ATIVO) {
                ids.add(aluguel.getId().toString());
            }
        }
        if (!ids.isEmpty()) {
            throw new AluguelAtivoException("Não foi possível remover o locatário de ID '" +
                    locatario.getId() + "' pois os alugueis dos seguite ID's estão " +
                    "pendentes de devolução: ", ids);
        }
    }
}
