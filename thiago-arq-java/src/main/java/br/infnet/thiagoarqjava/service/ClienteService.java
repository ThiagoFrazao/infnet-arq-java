package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.repository = clienteRepository;

    }

    public Cliente findClienteById(Long idCliente) {
        try {
            return this.repository.findById(idCliente).orElseThrow(() -> new IdentificadorInvalidoException(idCliente, "CLIENTE"));
        } catch (IdentificadorInvalidoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao acessar");
            throw new AcessoBancoDadosException("Falha ao recuperar cliente pelo ID %s".formatted(idCliente));
        }
    }

}