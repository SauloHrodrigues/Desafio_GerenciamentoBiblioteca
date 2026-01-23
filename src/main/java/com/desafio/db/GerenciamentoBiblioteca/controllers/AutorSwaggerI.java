package com.desafio.db.GerenciamentoBiblioteca.controllers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Modulo de Autor.", description = "Endpoints para fazer a gestão dos autores.")
public interface AutorSwaggerI {

    @Operation(
            summary = "Cadastrar autor",
            description = "Realiza o cadastro de um novo autor no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    ResponseEntity<AutorResponse> cadastrar(
            @Parameter(description = "Dados necessários para cadastrar um autor", required = true)
            AutorRequest dto
    );


    @Operation(
            summary = "Atualizar autor", description = "Atualiza parcialmente os dados de um autor a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    ResponseEntity<AutorResponse> atualizar(
            @Parameter(description = "ID do autor", example = "1", required = true) Long id,
            @Parameter(description = "Dados para atualização do autor", required = true)
           AutorAtualiza atualizacoes
    );


    @Operation(
            summary = "Apagar autor", description = "Remove um autor do sistema a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Autor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    ResponseEntity<Void> apagar(
            @Parameter(description = "ID do autor", example = "1", required = true) Long id
    );


    @Operation(
            summary = "Buscar autor por nome", description = "Busca um autor pelo nome informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    ResponseEntity<AutorResponse> buscarPorNome(
            @Parameter(description = "Nome do autor para busca", example = "Machado de Assis", required = true)
           String nome
    );


    @Operation(
            summary = "Buscar autor por ID",  description = "Busca os dados de um autor a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    ResponseEntity<AutorResponse> buscarPorId(
            @Parameter(description = "ID do autor", example = "1", required = true) Long id
    );


    @Operation(
            summary = "Listar autores", description = "Retorna uma lista paginada de autores cadastrados no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos")
    })
    ResponseEntity<Page<AutorResponse>> buscar(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"nome"})
            Pageable pageable
    );
}
