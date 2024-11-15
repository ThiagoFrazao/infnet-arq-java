package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.Empregado;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService) {
        this.repository = clienteRepository;
        this.enderecoService = enderecoService;
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

    public List<Cliente> salvarClientes(List<Cliente> empregados) {
        try {
            List<Cliente> clientesSalvos = new ArrayList<>();
            for(Cliente cliente : empregados) {
                Cliente clienteSalvo = this.salvarCliente(cliente);
                if(clienteSalvo != null) {
                    clientesSalvos.add(clienteSalvo);
                }
            }
            return clientesSalvos;
        } catch (Exception e) {
            log.error("Falha ao salvar empregados {}", empregados, e);
            throw new AcessoBancoDadosException("Falha ao salvar clientes");
        }
    }

    private Cliente salvarCliente(Cliente cliente) {
        try {
            cliente.setEndereco(this.enderecoService.salvarEndereco(cliente.getEndereco()));
            return repository.save(cliente);
        } catch (Exception e){
            log.error("Falha ao salvar cliente {}. Nao sera salvo no banco", cliente, e);
            return null;
        }
    }

}