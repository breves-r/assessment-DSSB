package com.infnet.at;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.at.controller.DepartamentoController;
import com.infnet.at.model.Departamento;
import com.infnet.at.service.DepartamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartamentoController.class)
public class DepartamentoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllDepartamentos() throws Exception {
        Departamento departamento1 = new Departamento(1L, "Finance", "Building A", null);
        Departamento departamento2 = new Departamento(2L, "HR", "Building B", null);

        given(departamentoService.getAll()).willReturn(Arrays.asList(departamento1, departamento2));

        mockMvc.perform(get("/api/departamento"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value(departamento1.getNome()))
                .andExpect(jsonPath("$[1].nome").value(departamento2.getNome()));
    }

    @Test
    public void testGetDepartamentoById() throws Exception {
        Departamento departamento = new Departamento(1L, "Finance", "Building A", null);

        given(departamentoService.getById(anyLong())).willReturn(departamento);

        mockMvc.perform(get("/api/departamento/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()));
    }

    @Test
    public void testSaveDepartamento() throws Exception {
        Departamento departamento = new Departamento(null, "Finance", "Building A", null);

        given(departamentoService.save(any(Departamento.class))).willReturn(departamento);

        mockMvc.perform(post("/api/departamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()));
    }

    @Test
    public void testUpdateDepartamento() throws Exception {
        Departamento departamento = new Departamento(1L, "Finance", "Building A", null);

        given(departamentoService.update(anyLong(), any(Departamento.class))).willReturn(departamento);

        mockMvc.perform(put("/api/departamento/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(departamento.getNome()));
    }

    @Test
    public void testDeleteDepartamento() throws Exception {
        mockMvc.perform(delete("/api/departamento/1"))
                .andExpect(status().isAccepted());
    }
}
