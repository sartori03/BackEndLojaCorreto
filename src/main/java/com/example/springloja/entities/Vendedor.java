package com.example.springloja.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vendedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do Vendedor gerado pelo banco", example = "1")
    private Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Schema(description = "Nome do Vendedor", example = "Teclado")
    private String nome;

    @Schema(description = "Foto do Vendedor", example = "imagem.jpg")
    private String foto;

    @JsonIgnore
    @OneToMany(mappedBy = "vendedor")
    private List<Venda> venda = new ArrayList<>();

    public Vendedor() {
    }

    public Long getId() {
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Venda> getVenda() {
        return venda;
    }

    public void setVenda(List<Venda> venda) {
        this.venda = venda;
    }
}
