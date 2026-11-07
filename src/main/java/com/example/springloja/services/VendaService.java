package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Venda;
import com.example.springloja.entities.Produto;
import com.example.springloja.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        repository.save(venda);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    public List<Venda> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public Venda buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
}


