package com.desafio.db.GerenciamentoBiblioteca.controllers;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Modulo de Locatário.", description = "Endpoints para fazer a gestão dos locatarios.")
public interface LocatarioSwaggerI {

    @Operation(
            summary = "Cadastrar um novo locatário",
            description = "Realiza o cadastro de um novo locatário no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Locatário cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocatarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    ResponseEntity<LocatarioResponse> cadastrar(
            @Parameter(description = "Dados necessários para cadastrar um locatário", required = true)
            LocatarioRequest dto
    );


    @Operation(
            summary = "Atualizar locatário",
            description = "Atualiza parcialmente os dados de um locatário existente."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Locatário atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LocatarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Locatário não encontrado")
    })
    ResponseEntity<LocatarioResponse> atualizar(
            @Parameter(description = "ID do locatário", example = "1", required = true)
            Long id,
            @Parameter(description = "Dados para atualização do locatário", required = true)
            LocatarioAtualiza atualizacoes
    );


    @Operation(
            summary = "Apagar locatário",
            description = "Remove um locatário do sistema a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Locatário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Locatário não encontrado")
    })
    ResponseEntity<Void> apagar(
            @Parameter(description = "ID do locatário", example = "1", required = true)
            Long id
    );


    @Operation(
            summary = "Listar locatários",
            description = "Retorna uma lista paginada de locatários cadastrados."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de locatários retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LocatarioResponse.class)))}
    )
    ResponseEntity<Page<LocatarioResponse>> buscar(
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable
    );


    @Operation(
            summary = "Buscar locatário por ID",
            description = "Retorna os dados de um locatário a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Locatário encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LocatarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Locatário não encontrado")
    })
    ResponseEntity<LocatarioResponse> buscarPorId(
            @Parameter(description = "ID do locatário", example = "1", required = true)
            Long id
    );


    @Operation(
            summary = "Listar aluguéis do locatário",
            description = "Retorna o locatário com todos os seus aluguéis associados."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Aluguéis do locatário retornados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LocatarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Locatário não encontrado")
    })
    ResponseEntity<LocatarioResponse> buscarAlugueisPorLocatario(
            @Parameter(description = "ID do locatário", example = "1", required = true)
            Long id
    );
}