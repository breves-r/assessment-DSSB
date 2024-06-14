package com.infnet.at.service;

import com.infnet.at.model.Usuario;
import com.infnet.at.model.Usuario;
import com.infnet.at.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getById(String id) {
        checkUsuarioExists(id);
        return usuarioRepository.findById(id).get();
    }

    public Usuario getByEmail(String email) {
        return usuarioRepository.findUsuarioByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        if (usuarioRepository.findUsuarioByEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Email já está em uso!");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario update(String id, Usuario usuario) {
        checkUsuarioExists(id);
        if (!usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public void deleteById(String id) {
        checkUsuarioExists(id);
        usuarioRepository.deleteById(id);
    }

    public void checkUsuarioExists(String id) {
        if (!usuarioRepository.existsById(id)) throw new EntityNotFoundException("Usuario não encontrado");
    }
}
