package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Filme;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.FilmeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FilmeService {

    private final FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> salvarFilmes(List<Filme> filmes) {
        try {
            return this.repository.saveAll(filmes);
        } catch (Exception e) {
            log.error("Falha ao salvar filmes", e);
            throw new AcessoBancoDadosException("Falha ao salvar filmes.");
        }
    }

    public List<Filme> recuperarTodasFilmes() {
        try {
            return this.repository.findAll();
        } catch (Exception e) {
            log.error("Falha ao recuperar todos os filmes", e);
            throw new AcessoBancoDadosException("Falha ao recuperar todos os filmes");
        }
    }

    public Filme recuperarFilmePorId(Long id) {
        try {
            return this.repository.findById(id).orElseThrow(() -> new IdentificadorInvalidoException(id, "FILME"));
        } catch (IdentificadorInvalidoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao recuperar filme por id {}", id, e);
            throw new AcessoBancoDadosException("Falha ao recuperar filme com id: %s".formatted(id));
        }
    }

    public Filme salvarFilme(Filme filme) {
        try {
            return this.repository.save(filme);
        } catch (Exception e) {
            log.error("Falha ao salvar filme", e);
            throw new AcessoBancoDadosException("Falha ao salvar filme: %s".formatted(filme.getNome()));
        }
    }
}
