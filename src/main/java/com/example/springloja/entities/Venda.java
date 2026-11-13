package com.example.springloja.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da Venda gerado pelo banco", example = "1")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Data", example = "01/01/2001")
    private LocalDate data;

    @Min(value = 0, message = "Minimo da venda é 0")
    private Float venda;

    @NotNull(message = "O cliente é obrigatório!")
    @Schema(description = "Cliente", example = "Jairzinho")
    @ManyToOne
    private Cliente cliente;

    @NotNull(message = "O vendedor é obrigatório!")
    @Schema(description = "Vendedor", example = "Juliana Banana")
    @ManyToOne
    private Vendedor vendedor;


    public Venda()   {
    }

    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getVenda() {
        return venda;
    }

    public void setVenda(Float venda) {
        this.venda = venda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}
