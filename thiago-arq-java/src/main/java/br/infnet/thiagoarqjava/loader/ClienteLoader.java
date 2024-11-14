package br.infnet.thiagoarqjava.loader;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.events.ClientesCarregadosEvent;
import br.infnet.thiagoarqjava.repository.ClienteRepository;
import br.infnet.thiagoarqjava.service.EnderecoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ClienteLoader {

    private final ObjectMapper objectMapper;
    private final ResourceLoader loader;
    private static final String CLASSPATH_PREFIX = "classpath:loadfiles/clientLoadFile.json";
    private final ClienteRepository repository;
    private final EnderecoService enderecoService;
    private final ApplicationEventPublisher eventPublisher;

    public ClienteLoader(ResourceLoader resourceLoader, ClienteRepository clienteRepository, EnderecoService enderecoService, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = new ObjectMapper();
        this.loader = resourceLoader;
        this.repository = clienteRepository;
        this.enderecoService = enderecoService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void carregarClientes() {
        final Resource clientLoad = this.loader.getResource(CLASSPATH_PREFIX);
        try(InputStream inputStream = clientLoad.getInputStream()) {
            final List<Cliente> clientes = this.objectMapper.readValue(inputStream, new TypeReference<List<Cliente>>() {});
            List<Cliente> novosClientes = new ArrayList<>();
            for (final Cliente cliente : clientes) {
                final Cliente clienteSalvo = this.salvarCliente(cliente);
                if(clienteSalvo != null) {
                    novosClientes.add(clienteSalvo);
                }
            }
            this.eventPublisher.publishEvent(new ClientesCarregadosEvent(novosClientes));
        } catch (Exception e) {
            log.error("Falha ao carregar clientes", e);
        }
    }

    private Cliente salvarCliente(Cliente cliente) {
        try {
            cliente.setEndereco(this.enderecoService.salvarEndereco(cliente.getEndereco()));
            return this.repository.save(cliente);
        } catch (Exception e){
            log.error("Falha ao salvar Cliente! Cliente sera ignorado");
            return null;
        }
    }

}
