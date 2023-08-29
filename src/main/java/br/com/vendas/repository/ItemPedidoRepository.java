package br.com.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.model.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {
    
}
