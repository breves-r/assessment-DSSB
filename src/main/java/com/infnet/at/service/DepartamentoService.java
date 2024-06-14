package com.infnet.at.service;

import com.infnet.at.model.Departamento;
import com.infnet.at.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> getAll() {
        return departamentoRepository.findAll();
    }

    public Departamento getById(Long id) {
        checkDepartamentoExists(id);
        return departamentoRepository.findById(id).get();
    }

    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento update(Long id, Departamento departamento) {
        checkDepartamentoExists(id);
        departamento.setId(id);
        return departamentoRepository.save(departamento);
    }

    public void deleteById(Long id) {
        checkDepartamentoExists(id);
        departamentoRepository.deleteById(id);
    }

    public void checkDepartamentoExists(Long id) {
        if (!departamentoRepository.existsById(id)) throw new EntityNotFoundException("Departamento não encontrado");
    }
}
