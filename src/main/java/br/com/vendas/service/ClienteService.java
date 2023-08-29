package br.com.vendas.service;

import br.com.vendas.model.entity.Cliente;
import br.com.vendas.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    private final  ClienteRepository clienteRepository;

    public Cliente save (Cliente cliente) {
        cliente.setName(cliente.getName());

        return clienteRepository.save(cliente);
    }
}
