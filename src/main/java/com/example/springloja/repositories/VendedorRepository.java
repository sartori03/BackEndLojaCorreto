package com.example.springloja.repositories;

import com.example.springloja.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    List<Vendedor> findByNomeContainingIgnoreCase(String nome);
}
