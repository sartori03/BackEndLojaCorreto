package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.entities.Cliente;
import com.example.springloja.entities.Produto;
import com.example.springloja.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> listarTodas() {
        return repository.findAll();
    }

    public void salvar(Cliente Cliente) {
        repository.save(Cliente);
    }

    public void atualizar(Cliente cliente){
        Cliente existente = repository.findById(cliente.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        existente.setNome(cliente.getNome());
        existente.setRenda(cliente.getRenda());
        repository.save(existente);
    }

    public void excluir(Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        repository.deleteById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
}


