package br.com.vendas.service;

import br.com.vendas.model.entity.Produto;
import br.com.vendas.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    private final ProdutoRepository produtoRepository;

    public Produto save (Produto produto) {
        return produtoRepository.save(produto);
    }
}
