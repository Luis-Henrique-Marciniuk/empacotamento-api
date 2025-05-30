package com.l2code.packaging.service;

import com.l2code.packaging.dto.CaixaDTO;
import com.l2code.packaging.dto.PedidoDTO;
import com.l2code.packaging.dto.PedidoEmpacotadoDTO;
import com.l2code.packaging.dto.ProdutoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpacotamentoService {

    private static class Caixa {
        String tipo;
        double altura;
        double largura;
        double comprimento;

        public Caixa(String tipo, double altura, double largura, double comprimento) {
            this.tipo = tipo;
            this.altura = altura;
            this.largura = largura;
            this.comprimento = comprimento;
        }

        public double getVolume() {
            return altura * largura * comprimento;
        }
    }

    private final List<Caixa> caixasDisponiveis = List.of(
            new Caixa("Caixa 1: 30x40x80", 30, 40, 80),
            new Caixa("Caixa 2: 80x50x40", 80, 50, 40),
            new Caixa("Caixa 3: 50x80x60", 50, 80, 60)
    );

    public List<CaixaDTO> empacotar(PedidoDTO pedido) {
        List<ProdutoDTO> produtosRestantes = new ArrayList<>(pedido.getProdutos());
        List<CaixaDTO> caixasUsadas = new ArrayList<>();

        while (!produtosRestantes.isEmpty()) {
            ProdutoDTO produto = produtosRestantes.remove(0);
            Caixa melhorCaixa = escolherMelhorCaixa(produto);

            CaixaDTO caixaDTO = new CaixaDTO(melhorCaixa.tipo);
            caixaDTO.getProdutos().add(produto);

            List<ProdutoDTO> produtosParaRemover = new ArrayList<>();
            for (ProdutoDTO p : produtosRestantes) {
                if (produtoCabeNaCaixa(p, caixaDTO, melhorCaixa)) {
                    caixaDTO.getProdutos().add(p);
                    produtosParaRemover.add(p);
                }
            }
            produtosRestantes.removeAll(produtosParaRemover);

            caixasUsadas.add(caixaDTO);
        }

        return caixasUsadas;
    }

    public List<PedidoEmpacotadoDTO> empacotar(List<PedidoDTO> pedidos) {
        List<PedidoEmpacotadoDTO> resultado = new ArrayList<>();
        for (PedidoDTO pedido : pedidos) {
            List<CaixaDTO> caixas = empacotar(pedido);
            resultado.add(new PedidoEmpacotadoDTO(pedido.getId(), caixas));
        }
        return resultado;
    }

    private Caixa escolherMelhorCaixa(ProdutoDTO produto) {
        for (Caixa caixa : caixasDisponiveis) {
            if (produto.getAltura() <= caixa.altura &&
                    produto.getLargura() <= caixa.largura &&
                    produto.getComprimento() <= caixa.comprimento) {
                return caixa;
            }
        }
        return caixasDisponiveis.get(caixasDisponiveis.size() - 1);
    }

    private boolean produtoCabeNaCaixa(ProdutoDTO produto, CaixaDTO caixaDTO, Caixa caixa) {
        return produto.getAltura() <= caixa.altura &&
                produto.getLargura() <= caixa.largura &&
                produto.getComprimento() <= caixa.comprimento;
    }
}
