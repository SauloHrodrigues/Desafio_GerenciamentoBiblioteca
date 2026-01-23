package com.desafio.db.GerenciamentoBiblioteca.controllers.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.controllers.LocatarioSwaggerI;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioAtualiza;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.locatario.LocatarioResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.LocatarioServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/locatarios")
public class LocatarioControllerI implements LocatarioSwaggerI {
    private final LocatarioServiceI serviceI;

    @Override
    @PostMapping
    public ResponseEntity<LocatarioResponse> cadastrar(@RequestBody @Valid LocatarioRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<LocatarioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid LocatarioAtualiza atualizacoes){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.atualizar(id,atualizacoes));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        serviceI.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<LocatarioResponse>> buscar( Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodos(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LocatarioResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.buscarPorId(id));
    }

    @Override
    @GetMapping("/id/alugueis")
    public ResponseEntity<LocatarioResponse> buscarAlugueisPorLocatario(Long id){
        return ResponseEntity.status(HttpStatus.OK).body(serviceI.listarTodosAlugueis(id));
    }
}