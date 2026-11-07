package com.example.springloja.controller;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Cliente;
import com.example.springloja.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public String salvar(@Valid @RequestBody Cliente Cliente) {
        service.salvar(Cliente);
        return "Cliente salvo com sucesso!";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id,@Valid @RequestBody Cliente Cliente){
        Cliente.setId(id);
        service.atualizar(Cliente);
        return "Cliente atualizado com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        service.excluir(id);
        return "Cliente deletado com sucesso!";
    }

    @GetMapping("/filtro-nome")
    public List<Cliente> filtrarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
        Cliente obj = service.buscarPorId(id);

        if (obj != null){
            return ResponseEntity.ok().body(obj);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
