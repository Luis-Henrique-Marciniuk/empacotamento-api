package com.l2code.packaging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoEmpacotadoDTO {
    private String id;
    private List<CaixaDTO> caixas;
}
