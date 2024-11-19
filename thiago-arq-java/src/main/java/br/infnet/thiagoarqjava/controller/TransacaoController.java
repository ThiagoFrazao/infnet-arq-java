package br.infnet.thiagoarqjava.controller;

import br.infnet.thiagoarqjava.domain.TipoTransacao;
import br.infnet.thiagoarqjava.domain.Transacao;
import br.infnet.thiagoarqjava.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<Transacao>> recuperarTransacoesPorCliente(@PathVariable Long idCliente, @RequestParam(required = false) TipoTransacao tipoTransacao) {
        final List<Transacao> transacoesCliente = this.service.recuperarTransacaoPorCliente(idCliente, tipoTransacao);
        if (transacoesCliente.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(transacoesCliente);
        }
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody Transacao transacao) {
        return ResponseEntity.ok(this.service.salvarTransacao(transacao));
    }

}
