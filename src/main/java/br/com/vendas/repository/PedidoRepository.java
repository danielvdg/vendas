package br.com.vendas.repository;

import br.com.vendas.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.model.entity.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join p.itens where p.id = :id")
    Optional<Pedido> findIdFetchItens(@Param("id") Long id);
    
}
