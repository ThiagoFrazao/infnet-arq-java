package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.TipoTransacao;
import br.infnet.thiagoarqjava.domain.Transacao;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.ClienteRepository;
import br.infnet.thiagoarqjava.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransacaoService {

    private final TransacaoRepository repository;
    private final ClienteRepository clienteRepository;

    public TransacaoService(TransacaoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
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
            return this.repository.saveAll(transacoes);
        } catch (Exception e) {
            log.error("Falha ao salvar transacoes {}", transacoes, e);
            throw new AcessoBancoDadosException("Falha ao salvar novas transacoes.");
        }
    }
}
