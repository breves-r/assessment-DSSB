package com.infnet.at.service;

import com.infnet.at.model.Funcionario;
import com.infnet.at.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> getAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario getById(Long id) {
        checkFuncionarioExists(id);
        return funcionarioRepository.findById(id).get();
    }

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario update(Long id, Funcionario funcionario) {
        checkFuncionarioExists(id);
        funcionario.setId(id);
        return funcionarioRepository.save(funcionario);
    }

    public void deleteById(Long id) {
        checkFuncionarioExists(id);
        funcionarioRepository.deleteById(id);
    }

    public void checkFuncionarioExists(Long id) {
        if (!funcionarioRepository.existsById(id)) throw new EntityNotFoundException("Funcionário não encontrado");
    }
}
