package br.com.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    
}
