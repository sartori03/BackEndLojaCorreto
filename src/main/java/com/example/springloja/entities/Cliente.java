package com.example.springloja.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da Cliente gerado pelo banco", example = "1")
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Schema(description = "Nome da Cliente", example = "Eletrônicos")
    private String nome;

    @JsonIgnore
    @Min(value = 0, message = "Minimo de renda é 0")
    private Float renda;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Venda> venda = new ArrayList<>();

    public Cliente() {
    }

    public   Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getRenda() {
        return renda;
    }

    public void setRenda(Float renda) {
        this.renda = renda;
    }

    public List<Venda> getVenda() {
        return venda;
    }

    public void setVenda(List<Venda> venda) {
        this.venda = venda;
    }
}
