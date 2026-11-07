package com.example.springloja.dto;

import com.example.springloja.entities.Venda;

import java.time.LocalDate;

public record VendaDTO(
        Long id,
        LocalDate data,
        Float venda,
        String nomeCliente,
        String nomeVendedor
) {
    public VendaDTO(Venda v){
        this(v.getId(), v.getData(), v.getVenda(), v.getCliente().getNome(), v.getVendedor().getNome());
    };
}

