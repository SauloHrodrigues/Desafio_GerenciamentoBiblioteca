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

 @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<Object> handlerLivroNaoEncontradoException(LivroNaoEncontradoException mensagem) {
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

    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<Object> handlerLivroIndisponivelException(LivroIndisponivelException mensagem) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( mensagem.getMessage());
    }

    @ExceptionHandler(IsbnJaExistenteException.class)
    public ResponseEntity<Object> handlerIsbnJaExitenteException(IsbnJaExistenteException mensagem) {
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

    @ExceptionHandler(LivrosIndisponiveisException.class)
    public ResponseEntity<?> handleLivrosIndisponiveis(LivrosIndisponiveisException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("mensagem", ex.getMessage());
        body.put("livrosIndisponiveis", ex.getTitulos());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}