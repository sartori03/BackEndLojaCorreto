package com.example.springloja.services;

import com.example.springloja.dto.VendedorDTO;
import com.example.springloja.entities.Vendedor;
import com.example.springloja.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendedor não encontrado: " + id));

        // Atualiza os campos normais
        vendedorExistente.setNome(dadosAtualizados.getNome());

        String fotoAnterior = vendedorExistente.getFoto();
        if (novaFoto != null && !novaFoto.isEmpty()) {
            vendedorExistente.setFoto(storageService.salvarArquivo(novaFoto));
        }
        Vendedor salvo = repository.save(vendedorExistente);
        if (novaFoto != null && !novaFoto.isEmpty()) {
            apagarArquivoAposCommit(fotoAnterior);
        }
        return salvo;
    }

    public void excluir(Long id){
        Vendedor vendedor = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendedor não encontrado: " + id));
        repository.delete(vendedor);
        storageService.deletarArquivo(vendedor.getFoto());
    }

    private void apagarArquivoAposCommit(String caminho) {
        if (caminho == null || caminho.isBlank()) return;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    storageService.deletarArquivo(caminho);
                }
            });
        } else {
            storageService.deletarArquivo(caminho);
        }
    }

    public List<VendedorDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(VendedorDTO::new).toList();
    }

    public VendedorDTO buscarPorId(Long id){
        return repository.findById(id).stream().map(VendedorDTO::new).findFirst().orElse(null);
    }
}
