package com.infnet.at.controller;

import com.infnet.at.model.Departamento;
import com.infnet.at.service.DepartamentoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamento")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<Departamento>> getAll() {
        return ResponseEntity.ok(departamentoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(departamentoService.getById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Departamento> save(@RequestBody Departamento departamento) {
        Departamento createdDepartamento = departamentoService.save(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> update(@PathVariable Long id, @RequestBody Departamento departamento) {
        try {
            Departamento updatedDepartamento = departamentoService.update(id,departamento);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedDepartamento);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            departamentoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
