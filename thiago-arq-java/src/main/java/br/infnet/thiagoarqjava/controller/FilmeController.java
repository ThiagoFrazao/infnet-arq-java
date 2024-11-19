package br.infnet.thiagoarqjava.controller;

import br.infnet.thiagoarqjava.domain.Filme;
import br.infnet.thiagoarqjava.service.FilmeService;
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
@RequestMapping("/filme")
public class FilmeController {

    private final FilmeService filmeService;
    private final UriUtils uriUtil;

    public FilmeController(FilmeService filmeService, UriUtils uriUtil) {
        this.filmeService = filmeService;
        this.uriUtil = uriUtil;
    }

    @GetMapping
    public ResponseEntity<List<Filme>> recuperarFilmes() {
        return ResponseEntity.ok(this.filmeService.recuperarTodasFilmes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> recuperarFilme(@PathVariable Long id) {
        return ResponseEntity.ok(this.filmeService.recuperarFilmePorId(id));
    }

    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        final Filme novoFilme = this.filmeService.salvarFilme(filme);
        if(novoFilme != null) {
            try{
                return ResponseEntity.created(this.uriUtil.crirUriNovoRecurso("/filme",
                        novoFilme.getId())).body(novoFilme);
            } catch (Exception e) {
                log.debug("Nao foi possivel gerar URI da nova filme");
                return ResponseEntity.created(null).body(novoFilme);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
