package com.example.springloja.services;

import com.example.springloja.dto.VendaDTO;
import com.example.springloja.dto.VendaDTO;
import com.example.springloja.entities.Venda;
import com.example.springloja.entities.Venda;
import com.example.springloja.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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

    @Transactional
    public void atualizar(Long id, Venda venda) {
        Venda vendaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrado com id: " + id));

        // Atualiza os campos normais
        vendaExistente.setData(venda.getData());
        vendaExistente.setVenda(venda.getVenda());
        vendaExistente.setCliente(venda.getCliente());
        vendaExistente.setVendedor(venda.getVendedor());

        repository.save(vendaExistente);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }

    public List<Venda> buscarPorNome(String nome) {
        return repository.findByClienteNomeContainingIgnoreCase(nome);
    }

    public Venda buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
}


