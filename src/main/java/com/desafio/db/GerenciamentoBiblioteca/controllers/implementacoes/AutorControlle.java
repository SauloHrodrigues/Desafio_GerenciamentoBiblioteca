package com.desafio.db.GerenciamentoBiblioteca.controllers.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.controllers.AutorSwaggerI;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.autor.AutorResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.AutorServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autores")
public class AutorControlle implements AutorSwaggerI {
    private final AutorServiceI serviceI;


    @PostMapping
    public ResponseEntity<AutorResponse> cadastrar(@RequestBody @Valid AutorRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AutorResponse> atualizar(@PathVariable Long id,@RequestBody @Valid AutorAtualiza atualizacoes){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.atualizar(id,atualizacoes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        serviceI.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/nome")
    public ResponseEntity<AutorResponse> buscarPorNome(  @RequestParam String nome){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorNome(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<AutorResponse>> buscar(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodos(pageable));
    }
}