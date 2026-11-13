package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Venda;
import com.example.springloja.entities.Produto;
import com.example.springloja.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    public List<Venda> listarTodas() {
        return repository.findAll();
    }

    public void salvar(Venda Venda) {
        repository.save(Venda);
    }

    public void atualizar(Venda venda){
        Venda existente = repository.findById(venda.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada"));
        existente.setData(venda.getData());
        existente.setVenda(venda.getVenda());
        existente.setCliente(venda.getCliente());
        existente.setVendedor(venda.getVendedor());
        repository.save(existente);
    }

    public void excluir(Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada");
        }
        repository.deleteById(id);
    }

    public List<Venda> buscarPorNome(String nome) {
        return repository.findByClienteNomeContainingIgnoreCase(nome);
    }

    public Venda buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
}


