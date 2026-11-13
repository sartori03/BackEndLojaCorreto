package com.example.springloja.services;

import com.example.springloja.dto.ProdutoDTO;
import com.example.springloja.dto.UsuarioDTO;
import com.example.springloja.entities.Categoria;
import com.example.springloja.entities.Usuario;
import com.example.springloja.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarTodos() {
        return repository.findAll().stream().map(UsuarioDTO::new).toList();
    }

    public void salvar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
    }

    public void atualizar(Usuario usuario){
        Usuario existente = repository.findById(usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        if (usuario.getUsername() != null && !usuario.getUsername().isBlank()) {
            existente.setUsername(usuario.getUsername());
        }
        if (usuario.getRole() != null) {
            existente.setRole(usuario.getRole());
        }
        // O frontend pode editar nome/perfil sem reenviar a senha.
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        repository.save(existente);
    }

    public void excluir(Long id){
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        repository.deleteById(id);
    }

    public UsuarioDTO buscarPorId(Long id){
        return repository.findById(id).stream().map(UsuarioDTO::new).findFirst().orElse(null);
    }
}


