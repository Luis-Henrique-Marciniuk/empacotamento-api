package com.l2code.packaging.service;

import com.l2code.packaging.dto.CaixaDTO;
import com.l2code.packaging.dto.PedidoDTO;
import com.l2code.packaging.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmpacotamentoServiceTest {

    private final EmpacotamentoService service = new EmpacotamentoService();

    @Test
    void deveEmpacotarProdutosOtimizadamente() {
        ProdutoDTO produto1 = new ProdutoDTO();
        produto1.setAltura(20);
        produto1.setLargura(30);
        produto1.setComprimento(40);

        ProdutoDTO produto2 = new ProdutoDTO();
        produto2.setAltura(10);
        produto2.setLargura(20);
        produto2.setComprimento(30);

        ProdutoDTO produto3 = new ProdutoDTO();
        produto3.setAltura(80);
        produto3.setLargura(50);
        produto3.setComprimento(40);

        PedidoDTO pedido = new PedidoDTO();
        pedido.setProdutos(List.of(produto1, produto2, produto3));

        List<CaixaDTO> caixas = service.empacotar(pedido);

        assertEquals(2, caixas.size(), "Deveria usar 2 caixas: uma com produto1+produto2 e outra com produto3");


        CaixaDTO caixa1 = caixas.get(0);
        assertEquals(2, caixa1.getProdutos().size());

        CaixaDTO caixa2 = caixas.get(1);
        assertEquals(1, caixa2.getProdutos().size());
    }
}
