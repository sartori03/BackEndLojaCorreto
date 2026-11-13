package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Produto;
import com.example.springloja.repositories.ProdutoRepository;
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
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private StorageService storageService;

    public List<ProdutoDTO> listarTodos() {
        return repository.findAll().stream().map(ProdutoDTO::new).toList();
    }

    public void salvar(Produto produto) {
        repository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto dadosAtualizados, MultipartFile novaFoto, MultipartFile novoManual) {
        Produto produtoExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + id));

        // Atualiza os campos normais
        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setPreco(dadosAtualizados.getPreco());
        produtoExistente.setCategoria(dadosAtualizados.getCategoria());

        String fotoAnterior = produtoExistente.getFoto();
        String manualAnterior = produtoExistente.getManual();
        if (novaFoto != null && !novaFoto.isEmpty()) {
            produtoExistente.setFoto(storageService.salvarArquivo(novaFoto));
        }
        if (novoManual != null && !novoManual.isEmpty()) {
            produtoExistente.setManual(storageService.salvarArquivo(novoManual));
        }

        Produto salvo = repository.save(produtoExistente);
        if (novaFoto != null && !novaFoto.isEmpty()) {
            apagarArquivoAposCommit(fotoAnterior);
        }
        if (novoManual != null && !novoManual.isEmpty()) {
            apagarArquivoAposCommit(manualAnterior);
        }
        return salvo;
    }

    public void excluir(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + id));
        repository.delete(produto);
        storageService.deletarArquivo(produto.getFoto());
        storageService.deletarArquivo(produto.getManual());
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

    public List<ProdutoDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(ProdutoDTO::new).toList();
    }

    public List<ProdutoDTO> buscarPorPrecoMaximo(Double preco) {
        return repository.findByPrecoLessThanEqual(preco).stream().map(ProdutoDTO::new).toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        return repository.findById(id).stream().map(ProdutoDTO::new).findFirst().orElse(null);
    }
}
