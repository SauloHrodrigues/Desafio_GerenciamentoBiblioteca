package com.desafio.db.GerenciamentoBiblioteca.dtos;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        long totalElements,
        int totalPages
) {}
