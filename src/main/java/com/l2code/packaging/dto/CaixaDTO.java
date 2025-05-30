package com.l2code.packaging.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CaixaDTO {
    private String tipoCaixa;
    private List<ProdutoDTO> produtos = new ArrayList<>();

    public CaixaDTO(String tipoCaixa) {
        this.tipoCaixa = tipoCaixa;
    }


}
