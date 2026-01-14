package com.desafio.db.GerenciamentoBiblioteca.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocatarioMapper {
    LocatarioMapper INSTANCE = Mappers.getMapper(LocatarioMapper.class);

}