package br.infnet.thiagoarqjava.controller;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {


    private final ClienteService service;

    public ClienteController(ClienteService clienteService) {
        this.service = clienteService;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long idCliente) {
        return ResponseEntity.ok(this.service.findClienteById(idCliente));
    }

}