package com.example.springloja.services;

import com.example.springloja.dto.ClienteDTO;
import com.example.springloja.entities.Cliente;
import com.example.springloja.entities.Cliente;
import com.example.springloja.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public void atualizar(Cliente cliente) {
        Cliente clienteExistente = repository.findById(cliente.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: "));

        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setRenda(cliente.getRenda());

        repository.save(clienteExistente);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
}


