package com.desafio.db.GerenciamentoBiblioteca.mappers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.entity.Locatario;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocatarioMapper {
    LocatarioMapper INSTANCE = Mappers.getMapper(LocatarioMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alugueis", ignore = true)
    @Mapping(target = "ativo", expression = "java(true)")
    @Mapping(target = "cpf", expression = "java(request.cpf().replaceAll(\"\\\\D\", \"\"))")
    @Mapping(target = "nome", expression = "java(request.nome() != null ? request.nome().toLowerCase() : null)")
    Locatario toEntity(LocatarioRequest request);

    @Mapping(target = "cpf", source = "cpf", qualifiedByName = "formata_cpf")
    LocatarioResponse toResponse(Locatario locatario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "nome", expression = "java(atualizacoes.nome() != null ? atualizacoes.nome().toLowerCase() : locatario.getNome())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(LocatarioAtualiza atualizacoes, @MappingTarget Locatario locatario);

    @Named("formata_cpf")
    default String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.replaceFirst(
                "(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4"
        );
    }
}