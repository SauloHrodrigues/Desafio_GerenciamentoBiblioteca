package com.desafio.db.GerenciamentoBiblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutorNaoEncontradoException.class)
    public ResponseEntity<Object> handlerAutorNaoEncontradoException(AutorNaoEncontradoException mensagem) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( mensagem.getMessage());
    }

    @ExceptionHandler(LocatarioNaoEncontradoException.class)
    public ResponseEntity<Object> handlerLocatarioNaoEncontradoException(LocatarioNaoEncontradoException mensagem) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( mensagem.getMessage());
    }

    @ExceptionHandler(CpfJaExistenteException.class)
    public ResponseEntity<Object> handlerCpfJaExitenteException(CpfJaExistenteException mensagem) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( mensagem.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        erros.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(erros);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
//            DataIntegrityViolationException ex
//    ) {
//
//        String mensagem = "Violação de integridade de dados.";
//
//        if (ex.getMessage() != null && ex.getMessage().contains("cpf")) {
//            mensagem = "CPF já cadastrado.";
//        }
//
//        Map<String, String> erro = Map.of(
//                "erro", mensagem
//        );
//
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
//    }


}