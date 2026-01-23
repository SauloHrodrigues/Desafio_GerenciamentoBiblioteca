package com.desafio.db.GerenciamentoBiblioteca.controllers.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.controllers.LivroSwaggerI;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.enun.CategoriaDeLivro;
import com.desafio.db.GerenciamentoBiblioteca.service.LivroServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroControllerI implements LivroSwaggerI {
    private final LivroServiceI serviceI;

    @Override
    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@RequestBody @Valid LivroRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable Long id, @RequestBody @Valid LivroAtualiza atualizacoes) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.atualizar(id, atualizacoes));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        serviceI.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<LivroResponse>> listarTodos(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodos(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorId(id));
    }

    @Override
    @GetMapping("/titulo")
    public ResponseEntity<LivroResponse> buscarPorTitulo(String titulo) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorTitulo(titulo));
    }

    @Override
    @GetMapping("/categoria")
    public ResponseEntity<Page<LivroResponse>> buscarPorNome(CategoriaDeLivro categoria, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorCategoria(categoria, pageable));
    }

    @Override
    @GetMapping("/id/autor")
    public ResponseEntity<Page<LivroResponse>> buscarPorIdDoAutor( Long id,Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorIdDoAutor(id,pageable));
    }
}