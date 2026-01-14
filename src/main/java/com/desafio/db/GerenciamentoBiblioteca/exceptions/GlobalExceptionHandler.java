package com.desafio.db.GerenciamentoBiblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutorNaoEncontradoException.class)
    public ResponseEntity<Object> handlerAutorNaoEncontradoException(AutorNaoEncontradoException mensagem) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( mensagem.getMessage());
    }
}