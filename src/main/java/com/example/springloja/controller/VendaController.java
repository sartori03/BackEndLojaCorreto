package com.example.springloja.controller;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Venda;
import com.example.springloja.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public String salvar(@Valid @RequestBody Venda Venda) {
        service.salvar(Venda);
        return "Venda salva com sucesso!";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,@Valid @RequestBody Venda Venda){
        Venda.setId(id);
        service.atualizar(Venda);
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
