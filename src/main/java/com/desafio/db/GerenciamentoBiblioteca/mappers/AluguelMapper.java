package com.desafio.db.GerenciamentoBiblioteca.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AluguelMapper {
    AluguelMapper INSTANCE = Mappers.getMapper(AluguelMapper.class);
}