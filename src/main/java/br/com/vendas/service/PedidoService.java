package br.com.vendas.service;

import br.com.vendas.model.entity.Pedido;
import br.com.vendas.model.dto.PedidoDTO;
import br.com.vendas.model.enums.StatusPedidoEnum;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Long id);

    void atualizarStatus(Long id, StatusPedidoEnum statusPedidoEnum);

}
