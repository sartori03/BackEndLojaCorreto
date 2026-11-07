package com.example.springloja.dto;

import com.example.springloja.entities.Cliente;

public record ClienteDTO(
        Long id,
        String nome,
        Float renda
) {
    public ClienteDTO(Cliente c){
        this(c.getId(), c.getNome(), c.getRenda());
    };
}

