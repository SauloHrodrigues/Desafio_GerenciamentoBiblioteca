package com.desafio.db.GerenciamentoBiblioteca.mappers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Aluguel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AluguelMapper {
    AluguelMapper INSTANCE = Mappers.getMapper(AluguelMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    @Mapping(target = "locatario", ignore = true)
    @Mapping(
            target = "devolucao",
            expression = "java( request.devolucao() != null ? request.devolucao() : request.retirada().plusDays(2) )"
    )
    Aluguel toEntity(AluguelRequest request);

    AluguelResponse toResponse(Aluguel aluguel);
}