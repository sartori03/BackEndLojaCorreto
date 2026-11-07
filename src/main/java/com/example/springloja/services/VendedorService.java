package com.example.springloja.services;

import com.example.springloja.dto.VendedorDTO;
import com.example.springloja.entities.Vendedor;
import com.example.springloja.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository repository;

    @Autowired
    private StorageService storageService;

    public List<VendedorDTO> listarTodos() {
        return repository.findAll().stream().map(VendedorDTO::new).toList();
    }

    public void salvar(Vendedor vendedor) {
        repository.save(vendedor);
    }

    @Transactional
    public Vendedor atualizar(Long id, Vendedor dadosAtualizados, MultipartFile novaFoto) {
        Vendedor vendedorExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado com id: " + id));

        // Atualiza os campos normais
        vendedorExistente.setNome(dadosAtualizados.getNome());

        if (novaFoto != null && !novaFoto.isEmpty()) {
            if (vendedorExistente.getFoto() != null) {
                storageService.deletarArquivo(vendedorExistente.getFoto());
            }

            String novoCaminho = storageService.salvarArquivo(novaFoto);
            vendedorExistente.setFoto(novoCaminho);
        }
        return repository.save(vendedorExistente);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    public List<VendedorDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(VendedorDTO::new).toList();
    }

    public VendedorDTO buscarPorId(Long id){
        return repository.findById(id).stream().map(VendedorDTO::new).findFirst().orElse(null);
    }
}
