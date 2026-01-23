package com.desafio.db.GerenciamentoBiblioteca.controllers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Modulo de aluguel.", description = "Endpoints para fazer a gestão dos alugueis.")
public interface AluguelSwaggerI {

    @Operation(
            summary = "Cadastrar um novo aluguel",
            description = "Realiza o cadastro de um novo aluguel associando um locatário aos livros informados."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Aluguel cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AluguelResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Locatário ou livro não encontrado")
    })
    ResponseEntity<AluguelResponse> cadastrar(
            @Parameter(description = "Dados necessários para cadastrar um aluguel", required = true)
            @RequestBody @Valid AluguelRequest dto);


    @Operation(summary = "Buscar livros", description = "Busca os livros associados a um recurso específico com paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    ResponseEntity<Page<LivroResponse>> buscarLivros(@Parameter(description = "ID do recurso a ser consultado",
            example = "1", required = true)
                                                     @PathVariable Long id, @Parameter(description = "Parâmetros de paginação e ordenação")
                                                     @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable
    );


    @Operation(
            summary = "Listar aluguéis",
            description = "Retorna uma lista paginada de aluguéis cadastrados no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de aluguéis retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AluguelResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos")
    })
    ResponseEntity<Page<AluguelResponse>> listarAlugueis(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"Locatario"})
            Pageable pageable
    );


    @Operation(
            summary = "Listar aluguéis por status",
            description = "Retorna uma lista paginada de aluguéis filtrados pelo status informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de aluguéis filtrada retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AluguelResponse.class))),
            @ApiResponse(responseCode = "400", description = "Status inválido ou parâmetros de paginação incorretos")
    })
    ResponseEntity<Page<AluguelResponse>> listarAlugueisComFiltro(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"Locatario"}) Pageable pageable,
            @Parameter(description = "Status do aluguel para filtro", example = "ATIVO", required = true)
            @RequestParam String status
    );


    @Operation(
            summary = "Buscar aluguel por ID",
            description = "Retorna os dados de um aluguel específico a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Aluguel encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AluguelResponse.class))),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    })
    ResponseEntity<AluguelResponse> buscarPorId(
            @Parameter(description = "ID do aluguel", example = "1", required = true) Long id
    );


    @Operation(
            summary = "Retorna aluguel",
            description = "Realiza a devolução de um aluguel a partir do ID informado, atualizando seu status e os livros associados."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Aluguel devolvido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    })
    ResponseEntity<Void> retornarAluguel(
            @Parameter(description = "ID do aluguel a ser devolvido", example = "1", required = true) Long id
    );

}