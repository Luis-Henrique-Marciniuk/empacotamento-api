package com.l2code.packaging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l2code.packaging.dto.CaixaDTO;
import com.l2code.packaging.dto.PedidoDTO;
import com.l2code.packaging.dto.PedidoEmpacotadoDTO;
import com.l2code.packaging.service.EmpacotamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpacotamentoController.class)
public class EmpacotamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpacotamentoService empacotamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEmpacotar() throws Exception {
        String jsonEntrada = """
        [
          {
            "id": "pedido1",
            "produtos": [
              {"altura": 10, "largura": 20, "comprimento": 30},
              {"altura": 15, "largura": 25, "comprimento": 35}
            ]
          }
        ]
        """;

        when(empacotamentoService.empacotar(org.mockito.ArgumentMatchers.<List<PedidoDTO>>any()))
                .thenReturn(List.of(
                        new PedidoEmpacotadoDTO("pedido1", List.of(new CaixaDTO("Caixa 1: 30x40x80")))
                ));

        mockMvc.perform(post("/api/empacotamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("pedido1"))
                .andExpect(jsonPath("$[0].caixas[0].tipoCaixa").value("Caixa 1: 30x40x80"));
    }
}
