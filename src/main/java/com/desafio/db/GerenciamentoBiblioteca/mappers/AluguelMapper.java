package com.desafio.db.GerenciamentoBiblioteca.mappers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper
public interface AluguelMapper {
    AluguelMapper INSTANCE = Mappers.getMapper(AluguelMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "status", expression = "java(StatusAluguel.ATIVO)")
    @Mapping(target = "locatario", ignore = true)
    @Mapping(
            target = "retirada",
            expression = "java(resolveRetirada(request.retirada()))"
    )
    @Mapping(
            target = "devolucao",
            expression = "java(resolveDevolucao(request.devolucao(), request.retirada()))"
    )
    Aluguel toEntity(AluguelRequest request);

    AluguelResponse toResponse(Aluguel aluguel);

    default LocalDate resolveRetirada(LocalDate retirada) {
        return retirada != null ? retirada : LocalDate.now();
    }

    default LocalDate resolveDevolucao(LocalDate devolucao, LocalDate retirada) {
        LocalDate base = retirada != null ? retirada : LocalDate.now();
        return devolucao != null ? devolucao : base.plusDays(2);
    }

}