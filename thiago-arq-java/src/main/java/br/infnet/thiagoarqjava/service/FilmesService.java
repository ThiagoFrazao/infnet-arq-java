package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Filme;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.repository.FilmeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FilmesService {

    private final FilmeRepository repository;

    public FilmesService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> salvarFilmes(List<Filme> filmes) {
        try {
            return this.repository.saveAll(filmes);
        } catch (Exception e) {
            log.error("Falha ao salvar filmes");
            throw new AcessoBancoDadosException("Falha ao salvar filmes.");
        }
    }

}
