package br.com.vendas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
}
