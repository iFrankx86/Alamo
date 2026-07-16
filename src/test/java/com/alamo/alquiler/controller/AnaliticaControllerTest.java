package com.alamo.alquiler.controller;

import com.alamo.alquiler.repository.ContratoAlquilerRepository;
import com.alamo.alquiler.repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnaliticaController.class)
public class AnaliticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContratoAlquilerRepository contratoRepo;

    @MockitoBean
    private VehiculoRepository vehiculoRepo;

    @Test
    void testObtenerAnaliticas() throws Exception {
        when(contratoRepo.findAll()).thenReturn(Collections.emptyList());
        when(vehiculoRepo.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/analiticas/dashboard"))
                .andExpect(status().isOk());

        verify(contratoRepo, times(1)).findAll();
        verify(vehiculoRepo, times(1)).findAll();
    }
}
