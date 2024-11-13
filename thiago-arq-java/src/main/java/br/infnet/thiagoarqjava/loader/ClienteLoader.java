package br.infnet.thiagoarqjava.loader;

import br.infnet.thiagoarqjava.domain.Cliente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ClienteLoader {

    private final ObjectMapper objectMapper;
    private final ResourceLoader loader;
    private static final String CLASSPATH_PREFIX = "classpath:loadfiles/clientLoadFile.json";

    public ClienteLoader(ResourceLoader resourceLoader) {
        this.objectMapper = new ObjectMapper();
        this.loader = resourceLoader;
    }

    public List<Cliente> carregarClientes() {
        final Resource clientLoad = this.loader.getResource(CLASSPATH_PREFIX);
        try(InputStream inputStream = clientLoad.getInputStream()) {
            return this.objectMapper.readValue(inputStream, new TypeReference<List<Cliente>>() {});
        } catch (Exception e) {
            log.error("Falha ao carregar clientes", e);
            return new ArrayList<>();
        }
    }


}
