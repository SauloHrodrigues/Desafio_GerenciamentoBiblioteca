package com.desafio.db.GerenciamentoBiblioteca.controllers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
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

@Tag(name = "Modulo de Livro.", description = "Endpoints para fazer a gestão dos livros.")
public interface LivroSwaggerI {

    @Operation(
            summary = "Cadastrar um livro",
            description = "Realiza o cadastro de um novo livro no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    ResponseEntity<LivroResponse> cadastrar(
            @Parameter(description = "Dados necessários para cadastrar um livro", required = true)
            @RequestBody @Valid LivroRequest dto
    );


    @Operation(
            summary = "Atualizar um livro",
            description = "Atualiza parcialmente os dados de um livro a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    ResponseEntity<LivroResponse> atualizar(
            @Parameter(description = "ID do livro", example = "1", required = true) Long id,
            @Parameter(description = "Dados para atualização do livro", required = true)
            @RequestBody @Valid LivroAtualiza atualizacoes
    );


    @Operation(
            summary = "Apagar livro", description = "Remove um livro do sistema a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Livro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    ResponseEntity<Void> apagar(
            @Parameter(description = "ID do livro", example = "1", required = true) Long id
    );


    @Operation(
            summary = "Listar livros", description = "Retorna uma lista paginada de livros cadastrados no sistema."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos")
    })
    ResponseEntity<Page<LivroResponse>> listarTodos(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"})
            Pageable pageable
    );


    @Operation(
            summary = "Buscar livro por ID",
            description = "Busca os dados de um livro a partir do ID informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    ResponseEntity<LivroResponse> buscarPorId(
            @Parameter(description = "ID do livro", example = "1", required = true) Long id
    );


    @Operation(
            summary = "Buscar livro por título", description = "Busca um livro pelo título informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    ResponseEntity<LivroResponse> buscarPorTitulo(
            @Parameter(description = "Título do livro para busca", example = "Dom Casmurro", required = true) String titulo
    );


    @Operation(
            summary = "Buscar livros por categoria", description = "Retorna uma lista paginada de livros filtrados pela categoria informada."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de livros por categoria retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Categoria inválida")
    })
    ResponseEntity<Page<LivroResponse>> buscarPorNome(
            @Parameter(description = "Categoria do livro", example = "ROMANCE", required = true)
            CategoriaDeLivro categoria, @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable
    );


    @Operation(
            summary = "Buscar livros por autor", description = "Retorna uma lista paginada de livros associados ao autor informado."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de livros do autor retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    ResponseEntity<Page<LivroResponse>> buscarPorIdDoAutor(
            @Parameter(description = "ID do autor", example = "1", required = true) Long id,
            @Parameter(description = "Parâmetros de paginação e ordenação") Pageable pageable
    );
}