package com.infnet.at;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.at.controller.FuncionarioController;
import com.infnet.at.model.Departamento;
import com.infnet.at.model.Funcionario;
import com.infnet.at.service.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
@WithMockUser(username = "user", password = "password", roles = "ADMIN")
public class FuncionarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllFuncionarios() throws Exception {
        Funcionario funcionario1 = new Funcionario(1L, "John Doe", "123 Main St", "123-456-7890", "john.doe@example.com", LocalDate.of(1980, 1, 1), null);
        Funcionario funcionario2 = new Funcionario(2L, "Jane Smith", "456 Elm St", "987-654-3210", "jane.smith@example.com", LocalDate.of(1990, 2, 2), null);

        given(funcionarioService.getAll()).willReturn(Arrays.asList(funcionario1, funcionario2));

        mockMvc.perform(get("/api/funcionario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value(funcionario1.getNome()))
                .andExpect(jsonPath("$[1].nome").value(funcionario2.getNome()));
    }

    @Test
    public void testGetFuncionarioById() throws Exception {
        Funcionario funcionario = new Funcionario(1L, "John Doe", "123 Main St", "123-456-7890", "john.doe@example.com", LocalDate.of(1980, 1, 1), null);

        given(funcionarioService.getById(anyLong())).willReturn(funcionario);

        mockMvc.perform(get("/api/funcionario/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }

    @Test
    public void testSaveFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario(null, "John Doe", "123 Main St", "123-456-7890", "john.doe@example.com", LocalDate.of(1980, 1, 1), null);

        given(funcionarioService.save(any(Funcionario.class))).willReturn(funcionario);

        mockMvc.perform(post("/api/funcionario")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }

    @Test
    public void testUpdateFuncionario() throws Exception {
        Funcionario funcionario = new Funcionario(1L, "John Doe", "123 Main St", "123-456-7890", "john.doe@example.com", LocalDate.of(1980, 1, 1), null);

        given(funcionarioService.update(anyLong(), any(Funcionario.class))).willReturn(funcionario);

        mockMvc.perform(put("/api/funcionario/1")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                        .andExpect(status().isAccepted())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }

    @Test
    public void testDeleteFuncionario() throws Exception {
        mockMvc.perform(delete("/api/funcionario/1").with(csrf().asHeader()))
                .andExpect(status().isAccepted());
    }
}
