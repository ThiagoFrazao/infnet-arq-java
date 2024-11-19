package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.Filme;
import br.infnet.thiagoarqjava.domain.TipoTransacao;
import br.infnet.thiagoarqjava.domain.Transacao;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.ClienteRepository;
import br.infnet.thiagoarqjava.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransacaoService {

    private final TransacaoRepository repository;
    private final ClienteRepository clienteRepository;
    private final FilmeService filmeService;

    public TransacaoService(TransacaoRepository repository, ClienteRepository clienteRepository, FilmeService filmeService) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.filmeService = filmeService;
    }

    public List<Transacao> recuperarTransacaoPorCliente(Long clienteId, TipoTransacao tipoTransacao) {
        try {
            final Cliente cliente = this.clienteRepository.findClienteById(clienteId);
            if(cliente == null) {
                throw new IdentificadorInvalidoException(clienteId, "Cliente");
            } else {
                if(tipoTransacao == null) {
                    return this.repository.findAllByClienteId(clienteId);
                } else {
                    return this.repository.findAllByClienteAndTipo(cliente, tipoTransacao);
                }
            }
        } catch (IdentificadorInvalidoException e) {
          throw e;
        } catch (Exception e) {
            log.error("Erro ao recuperar aluguel por Cliente", e);
            throw new AcessoBancoDadosException("Houve uma falha ao consultar os alugueis do cliente %s".formatted(clienteId));
        }
    }


    public List<Transacao> salvarTransacoes(List<Transacao> transacoes) {
        try {
            List<Transacao> retorno = new ArrayList<>();
            for(Transacao transacao : transacoes) {
                retorno.add(this.salvarTransacao(transacao));
            }
            return retorno;
        } catch (Exception e) {
            log.error("Falha ao salvar transacoes {}", transacoes, e);
            throw new AcessoBancoDadosException("Falha ao salvar novas transacoes.");
        }
    }

    public Transacao salvarTransacao(Transacao transacao) {
        try {
            for(Filme filmeTransacao : transacao.getFilmes()) {
                Filme filme = this.filmeService.recuperarFilmePorId(filmeTransacao.getId());
                this.filmeService.salvarFilme(this.atualizarFilme(filme, transacao.getTipo()));
            }
            return this.repository.save(transacao);
        } catch (Exception e) {
          log.error("Falha ao salvar transacao {}", transacao, e);
          throw new AcessoBancoDadosException("Falha ao salvar transacao.");
        }
    }

    private Filme atualizarFilme(Filme filme, TipoTransacao tipoTransacao) {
        switch (tipoTransacao) {
            case VENDA:
                filme.setQntTotal(filme.getQntTotal() - 1);
                filme.setQntDisponivel(filme.getQntDisponivel() - 1);
                return filme;
            case ALUGUEL:
                filme.setQntDisponivel(filme.getQntDisponivel() - 1);
                return filme;
            case RETORNO:
                filme.setQntTotal(filme.getQntDisponivel() + 1);
                return filme;
            default:
                log.debug("Tipo transacao {} nao mapeado ", tipoTransacao);
                return filme;
        }
    }
}
