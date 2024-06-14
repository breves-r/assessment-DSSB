package com.infnet.at;

import com.infnet.at.model.Departamento;
import com.infnet.at.model.Funcionario;
import com.infnet.at.model.Usuario;
import com.infnet.at.service.DepartamentoService;
import com.infnet.at.service.FuncionarioService;
import com.infnet.at.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create and save initial Departamentos
        Departamento departamento1 = new Departamento(null, "TI", "Andar 1", null);
        Departamento departamento2 = new Departamento(null, "RH", "Andar 2", null);
        departamentoService.save(departamento1);
        departamentoService.save(departamento2);

        // Create and save initial Funcionarios
        Funcionario funcionario1 = new Funcionario(null, "John Doe", "123 Main St", "123-456-7890", "john.doe@example.com", LocalDate.of(1980, 1, 1), departamento1);
        Funcionario funcionario2 = new Funcionario(null, "Jane Smith", "456 Elm St", "987-654-3210", "jane.smith@example.com", LocalDate.of(1990, 2, 2), departamento2);
        funcionarioService.save(funcionario1);
        funcionarioService.save(funcionario2);

        Usuario usuario = new Usuario(null, "admin", "admin@test.com", "senha", "ROLE_ADMIN");
        usuarioService.save(usuario);
    }
}
