package br.com.vendas.service.impl;

import br.com.vendas.exception.PedidoNaoEncontradoException;
import br.com.vendas.exception.RegraNegocioException;
import br.com.vendas.model.entity.Cliente;
import br.com.vendas.model.entity.ItemPedido;
import br.com.vendas.model.entity.Pedido;
import br.com.vendas.model.entity.Produto;
import br.com.vendas.model.dto.ItemPedidoDTO;
import br.com.vendas.model.dto.PedidoDTO;
import br.com.vendas.model.enums.StatusPedidoEnum;
import br.com.vendas.repository.ClienteRepository;
import br.com.vendas.repository.ItemPedidoRepository;
import br.com.vendas.repository.PedidoRepository;
import br.com.vendas.repository.ProdutoRepository;
import br.com.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoSeviceImpl implements PedidoService   {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;


    @Transactional
    public Pedido salvar (PedidoDTO pedidoDTO) {
        Long idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedidoEnum.REALIZADO);

        List<ItemPedido> itemsPedido = converterIntems(pedido, pedidoDTO.getItens());
        itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Long id) {
        return pedidoRepository.findIdFetchItens(id);
    }

    @Override
    public void atualizarStatus(Long id, StatusPedidoEnum statusPedidoEnum) {
        pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedidoEnum);
            return pedidoRepository.save(pedido);
        }).orElseThrow(PedidoNaoEncontradoException::new);
    }


    private List<ItemPedido> converterIntems(Pedido pedido, List<ItemPedidoDTO> items) {

        return  items
                .stream()
                .map(itemDto -> {
                    Long idProduto = itemDto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(itemDto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return  itemPedido;
                }).collect(Collectors.toList());
    }

}



