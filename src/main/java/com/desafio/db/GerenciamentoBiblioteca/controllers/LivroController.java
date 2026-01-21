package com.desafio.db.GerenciamentoBiblioteca.controllers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.service.LivroServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroServiceI serviceI;

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@RequestBody @Valid LivroRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable Long id, @RequestBody @Valid LivroAtualiza atualizacoes) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.atualizar(id, atualizacoes));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        serviceI.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping
    public ResponseEntity<Page<LivroResponse>> listarTodos(@PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorId(id));
    }


    @GetMapping("/titulo")
    public ResponseEntity<LivroResponse> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorTitulo(titulo));
    }

    @GetMapping("/categoria")
    public ResponseEntity<Page<LivroResponse>> buscarPorNome(@RequestParam CategoriaDeLivro categoria,
                                                             @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorCategoria(categoria, pageable));
    }

    @GetMapping("/id/autor")
    public ResponseEntity<Page<LivroResponse>> buscarPorIdDoAutor(@RequestParam Long id,Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorIdDoAutor(id,pageable));
    }
}