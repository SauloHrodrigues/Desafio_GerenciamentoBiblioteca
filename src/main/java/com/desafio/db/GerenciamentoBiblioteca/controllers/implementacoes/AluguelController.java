package com.desafio.db.GerenciamentoBiblioteca.controllers.implementacoes;

import com.desafio.db.GerenciamentoBiblioteca.controllers.AluguelSwaggerI;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelRequest;
import com.desafio.db.GerenciamentoBiblioteca.dtos.aluguel.AluguelResponse;
import com.desafio.db.GerenciamentoBiblioteca.dtos.livro.LivroResponse;
import com.desafio.db.GerenciamentoBiblioteca.service.AluguelServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alugueis")
public class AluguelController implements AluguelSwaggerI {
    private final AluguelServiceI serviceI;

    @Override
    @PostMapping
    public ResponseEntity<AluguelResponse> cadastrar(@RequestBody @Valid AluguelRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @Override
    @GetMapping("/{id}/todos_livros")
    public ResponseEntity<Page<LivroResponse>> buscarLivros(Long id, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body((serviceI.listarLivros(id,pageable)));
    }

    @GetMapping
    public ResponseEntity<Page<AluguelResponse>> listarAlugueis(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body((serviceI.listarAlugueis(pageable)));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<AluguelResponse>> listarAlugueisComFiltro(Pageable pageable, @RequestParam String status){
        return ResponseEntity.status(HttpStatus.OK).body((serviceI.listarAlugueis(status,pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AluguelResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body((serviceI.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> retornarAluguel(@PathVariable Long id){
        serviceI.devolverAluguel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}