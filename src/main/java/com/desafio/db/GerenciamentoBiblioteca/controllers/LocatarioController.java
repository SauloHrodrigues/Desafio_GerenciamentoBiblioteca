package com.desafio.db.GerenciamentoBiblioteca.controllers;

import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
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
@RequestMapping("/locatarios")
public class LocatarioController {
    private final LocatarioServiceI serviceI;

    @PostMapping
    public ResponseEntity<LocatarioResponse> cadastrar(@RequestBody @Valid LocatarioRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LocatarioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid LocatarioAtualiza atualizacoes){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.atualizar(id,atualizacoes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        serviceI.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<LocatarioResponse>> buscar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocatarioResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorId(id));
    }

    @GetMapping("/id/alugueis")
    public ResponseEntity<LocatarioResponse> buscarPorNome(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodosAlugueis(id));
    }
}