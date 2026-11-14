package com.example.springloja.controller;

import com.example.springloja.dto.VendaDTO;
import com.example.springloja.entities.Venda;
import com.example.springloja.entities.Venda;
import com.example.springloja.services.VendaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin
public class VendaController {
    @Autowired
    private VendaService service;

    @GetMapping
    public List<Venda> listar() {
        return service.listarTodas();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    public String salvar(@Valid @RequestPart("venda") Venda venda) {

        service.salvar(venda);
        return "Venda criada com sucesso!";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    public String atualizar(
            @PathVariable Long id,
            @Valid @RequestPart("venda") Venda venda) {

        service.atualizar(id, venda);
        return "Venda atualizada com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        service.excluir(id);
        return "Venda deletada com sucesso!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id){
        Venda obj = service.buscarPorId(id);

        if (obj != null){
            return ResponseEntity.ok().body(obj);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
