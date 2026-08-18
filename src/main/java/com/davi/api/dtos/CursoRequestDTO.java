package com.davi.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CursoRequestDTO(

        @NotBlank(message = "O campo 'name' é obrigatório!")
        String name,

        @NotBlank(message = "O campo 'category' é obrigatório!")
        String category,

        @NotBlank(message = "O campo 'professor' é obrigatório!")
        String professor

        ) {
}