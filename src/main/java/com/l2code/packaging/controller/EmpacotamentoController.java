package com.l2code.packaging.controller;

import com.l2code.packaging.dto.PedidoDTO;
import com.l2code.packaging.dto.PedidoEmpacotadoDTO;
import com.l2code.packaging.service.EmpacotamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empacotamento")
public class EmpacotamentoController {

    @Autowired
    private EmpacotamentoService empacotamentoService;

    @PostMapping
    public List<PedidoEmpacotadoDTO> empacotarPedidos(@RequestBody List<PedidoDTO> pedidos) {
        return empacotamentoService.empacotar(pedidos);
    }
}
