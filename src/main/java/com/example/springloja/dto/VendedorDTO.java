package com.example.springloja.dto;

import com.example.springloja.entities.Vendedor;

public record VendedorDTO(
        Long id,
        String nome,
        String foto
) {
    public VendedorDTO(Vendedor v){
        this(v.getId(), v.getNome(), v.getFoto());
    }
}

