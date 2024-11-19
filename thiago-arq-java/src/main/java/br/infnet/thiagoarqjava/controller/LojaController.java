package br.infnet.thiagoarqjava.controller;

import br.infnet.thiagoarqjava.domain.Loja;
import br.infnet.thiagoarqjava.service.LojaService;
import br.infnet.thiagoarqjava.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/loja")
public class LojaController {

    private final LojaService lojaService;
    private final UriUtils uriUtil;

    public LojaController(LojaService lojaService, UriUtils uriUtil) {
        this.lojaService = lojaService;
        this.uriUtil = uriUtil;
    }

    @GetMapping
    public ResponseEntity<List<Loja>> recuperarLojas() {
        return ResponseEntity.ok(this.lojaService.recuperarTodasLojas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loja> recuperarLoja(@PathVariable Long id) {
        return ResponseEntity.ok(this.lojaService.recuperarLojaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Loja> cadastrarLoja(@RequestBody Loja loja) {
        final Loja novaLoja = this.lojaService.salvarLoja(loja);
        if(novaLoja != null) {
            try{
                return ResponseEntity.created(this.uriUtil.crirUriNovoRecurso("/loja", novaLoja.getId())).body(novaLoja);
            } catch (Exception e) {
                log.debug("Nao foi possivel gerar URI da nova loja");
                return ResponseEntity.created(null).body(novaLoja);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
