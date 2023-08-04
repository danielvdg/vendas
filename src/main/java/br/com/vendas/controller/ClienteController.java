package br.com.vendas.controller;

import br.com.vendas.model.Cliente;
import br.com.vendas.repository.ClienteRepository;
import br.com.vendas.service.ClienteService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public ClienteController(ClienteRepository clienteRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity getClientesById(@PathVariable Long id) {

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()) {
            return  ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cliente> create (@RequestBody Cliente cliente) {
        clienteService.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping(path = "{id}")
    @ResponseBody
    public  ResponseEntity update(@PathVariable Long id,
                                  @RequestBody Cliente cliente) {
        return clienteRepository
                    .findById(id)
                    .map(clienteExistente -> {
                            cliente.setId(clienteExistente.getId());
                            clienteRepository.save(cliente);
                            return  ResponseEntity.noContent().build();

                    }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public  ResponseEntity find( Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example example = Example.of(filtro, matcher);
        List<Cliente> list = clienteRepository.findAll(example);
        return ResponseEntity.ok(list);
    }
}
