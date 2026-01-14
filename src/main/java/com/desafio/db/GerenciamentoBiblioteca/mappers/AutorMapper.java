package com.desafio.db.GerenciamentoBiblioteca.mappers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Autor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutorMapper {
    AutorMapper INSTANCE = Mappers.getMapper(AutorMapper.class);
    @Mapping(target = "nome", expression = "java(request.nome() != null ? request.nome().toLowerCase() : null)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    Autor toEntity(AutorRequest request);

    AutorResponse toResponse(Autor autor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome",expression = "java(atualizacoes.nome() != null ? atualizacoes.nome().toLowerCase() : autor.getNome())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(AutorAtualiza atualizacoes, @MappingTarget Autor autor);
}