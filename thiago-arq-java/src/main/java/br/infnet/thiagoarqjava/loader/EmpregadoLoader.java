package br.infnet.thiagoarqjava.loader;

import br.infnet.thiagoarqjava.domain.Empregado;
import br.infnet.thiagoarqjava.events.ClientesCarregadosEvent;
import br.infnet.thiagoarqjava.events.EmpregadosSalvosEvent;
import br.infnet.thiagoarqjava.service.EmpregadoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class EmpregadoLoader {

    private static final String CLASSPATH_PREFIX = "classpath:loadfiles/empregadoLoadFile.json";
    private final ResourceLoader resourceLoader;
    private final EmpregadoService service;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public EmpregadoLoader(ResourceLoader resourceLoader, EmpregadoService empregadoService, ApplicationEventPublisher eventPublisher) {
        this.resourceLoader = resourceLoader;
        this.service = empregadoService;
        this.eventPublisher = eventPublisher;
        this.objectMapper = new ObjectMapper();
    }

    @EventListener(ClientesCarregadosEvent.class)
    public void carregarEmpregados(ClientesCarregadosEvent event) {
        final Resource clientLoad = this.resourceLoader.getResource(CLASSPATH_PREFIX);
        try(InputStream inputStream = clientLoad.getInputStream()) {
            final List<Empregado> empregados = this.objectMapper.readValue(inputStream, new TypeReference<>() {});
            this.eventPublisher.publishEvent(new EmpregadosSalvosEvent(event.getClientes(),
                    this.service.salvarEmpregados(empregados)));
        } catch (Exception e){
            log.error("Falha ao carregar empregados", e);
        }
    }


}
