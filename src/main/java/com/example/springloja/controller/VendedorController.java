package com.example.springloja.controller;

import com.example.springloja.dto.VendedorDTO;
import com.example.springloja.entities.Vendedor;
import com.example.springloja.services.VendedorService;
import com.example.springloja.services.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vendedor")
@CrossOrigin
@Tag(name="Vendedor", description = "Gerenciamento vendedores")
public class VendedorController {
    @Autowired
    private VendedorService service;

    @GetMapping
    @Operation(summary = "Lista todos os vendedores", description = "Retorna uma lista completa dos vendedores")
    public List<VendedorDTO> listar() {
        return service.listarTodos();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    public String criar(@Valid @RequestPart("vendedor") Vendedor vendedor,
                        @RequestPart(value = "foto", required = false) MultipartFile foto)
 {

        if (foto != null && !foto.isEmpty()) {
            StorageService storageService = null;
            String caminho = storageService.salvarArquivo(foto);
            vendedor.setFoto(caminho);
        }

        service.salvar(vendedor);
        return "Vendedor " + vendedor.getNome() + " criado com sucesso!";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "bearerAuth")
    public String atualizar(
            @PathVariable Long id,
            @Valid @RequestPart("vendedor") Vendedor vendedor,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        service.atualizar(id, vendedor, foto);
        return "Vendedor atualizado com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id){
        service.excluir(id);
        return "Vendedor deletado com sucesso!";
    }

    @GetMapping("/filtro-nome")
    public List<VendedorDTO> filtrarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> buscarPorId(@PathVariable Long id){
        VendedorDTO obj = service.buscarPorId(id);

        if (obj != null){
            return ResponseEntity.ok().body(obj);
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
