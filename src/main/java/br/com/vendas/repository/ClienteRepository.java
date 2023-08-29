package br.com.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vendas.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Query(value = " select * from cliente c where c.name like '%:name%' ", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("name") String name);

    @Query(" delete from Cliente c where c.name =:name ")
    @Modifying
    void deleteByName(String name);

    boolean existsByName(String name);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id  ")
    Cliente findClientefetchPedidos(@Param("id") Long id);
    
}
