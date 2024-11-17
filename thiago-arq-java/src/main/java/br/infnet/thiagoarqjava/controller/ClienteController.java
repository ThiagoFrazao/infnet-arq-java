package br.infnet.thiagoarqjava.controller;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.service.ClienteService;
import br.infnet.thiagoarqjava.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final UriUtils uriUtil;

    public ClienteController(ClienteService clienteService, UriUtils uriUtil) {
        this.clienteService = clienteService;
        this.uriUtil = uriUtil;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> recuperarClientes() {
        return ResponseEntity.ok(this.clienteService.recuperarTodosClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> recuperarClientes(@PathVariable Long id) {
        return ResponseEntity.ok(this.clienteService.findClienteById(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        final Cliente novoCliente = this.clienteService.salvarCliente(cliente);
        if(novoCliente != null) {
            try{
                return ResponseEntity.created(this.uriUtil.crirUriNovoRecurso("/cliente", novoCliente.getId())).body(novoCliente);
            } catch (Exception e) {
                log.debug("Nao foi possivel gerar URI do novo cliente");
                return ResponseEntity.created(null).body(novoCliente);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
