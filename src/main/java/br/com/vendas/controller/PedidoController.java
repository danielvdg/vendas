package br.com.vendas.controller;

import br.com.vendas.model.Pedido;
import br.com.vendas.model.dto.PedidoDTO;
import br.com.vendas.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long save( @RequestBody PedidoDTO pedidoDTO ){
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

}
