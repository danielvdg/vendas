package br.com.vendas.service;

import br.com.vendas.model.Pedido;
import br.com.vendas.model.dto.PedidoDTO;
import org.springframework.stereotype.Service;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

}
