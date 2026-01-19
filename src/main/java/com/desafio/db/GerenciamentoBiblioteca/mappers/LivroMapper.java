package com.desafio.db.GerenciamentoBiblioteca.mappers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Livro;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface LivroMapper {
    LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(target = "alugueis", ignore = true)
    @Mapping(target = "statusLivro", expression = "java(StatusLivro.DISPONIVEL)")
    @Mapping(target = "titulo", expression = "java(request.titulo() != null ? request.titulo().toLowerCase() : null)")
    Livro toEntity(LivroRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(target = "alugueis", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(LivroAtualiza atualizacoes, @MappingTarget Livro livro);

    @Mapping(target = "id", source = "id")
    LivroResponse toResponse(Livro livro);

    List<LivroResponse> toResponse(List<Livro> livro);
}