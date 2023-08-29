package br.com.vendas.controller;

import br.com.vendas.model.entity.ItemPedido;
import br.com.vendas.model.entity.Pedido;
import br.com.vendas.model.dto.AtualizacaoStatusPedidoDTO;
import br.com.vendas.model.dto.InformacoesItemPedidoDTO;
import br.com.vendas.model.dto.InformacoesPedidoDTO;
import br.com.vendas.model.dto.PedidoDTO;
import br.com.vendas.model.enums.StatusPedidoEnum;
import br.com.vendas.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById( @PathVariable Long id ){
        return pedidoService
                .obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@ PathVariable Long id,
                             @RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO){

        String novoStatus = atualizacaoStatusPedidoDTO.getNovoStatus();
        pedidoService.atualizarStatus(id, StatusPedidoEnum.valueOf(novoStatus));

    }

    public InformacoesPedidoDTO converter(Pedido pedido) {
        return  InformacoesPedidoDTO
                    .builder()
                    .codigo(pedido.getId())
                    .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .cpf(pedido.getCliente().getCpf())
                    .nomeCliente(pedido.getCliente().getName())
                    .total(pedido.getTotal())
                    .status(pedido.getStatus().name())
                    .itens(converter(pedido.getItens()))
                    .build();
    }

    public List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.EMPTY_LIST;
        }

        return itens.stream().map(
                item -> InformacoesItemPedidoDTO
                            .builder()
                            .descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco())
                            .quantidade(item.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }

}
