package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Produto;
import com.example.springloja.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));

        // Atualiza os campos normais
        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setPreco(dadosAtualizados.getPreco());
        produtoExistente.setCategoria(dadosAtualizados.getCategoria());

        if (novaFoto != null && !novaFoto.isEmpty()) {
            if (produtoExistente.getFoto() != null) {
                storageService.deletarArquivo(produtoExistente.getFoto());
            }

            String novoCaminho = storageService.salvarArquivo(novaFoto);
            produtoExistente.setFoto(novoCaminho);
        }
        if (novoManual != null && !novoManual.isEmpty()) {
            if (produtoExistente.getManual() != null) {
                storageService.deletarArquivo(produtoExistente.getManual());
            }

            String novoCaminho = storageService.salvarArquivo(novoManual);
            produtoExistente.setManual(novoCaminho);
        }

        return repository.save(produtoExistente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
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
