package br.com.vendas.controller;

import br.com.vendas.model.entity.Cliente;
import br.com.vendas.repository.ClienteRepository;
import br.com.vendas.service.ClienteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    @Value("${application.name}")
    public String message;

    public ClienteController(ClienteRepository clienteRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @GetMapping(path = "/ambiente")
    public String testeAmbiente(String message){
        return this.message;
    }

    @GetMapping("/{id}")
    public Cliente getClientesById(@PathVariable Long id) {
        return clienteRepository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException( HttpStatus.NOT_FOUND,"Cliente não encontrado"));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create (@RequestBody @Valid Cliente cliente) {
        return clienteService.save(cliente);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteRepository
                .findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));

    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id,
                       @RequestBody Cliente cliente) {
        clienteRepository
                .findById(id)
                .map(clienteExistente -> {
                        cliente.setId(clienteExistente.getId());
                        clienteRepository.save(cliente);
                        return  clienteExistente;

                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro){
            ExampleMatcher matcher = ExampleMatcher
                .matchingAny()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }
}
