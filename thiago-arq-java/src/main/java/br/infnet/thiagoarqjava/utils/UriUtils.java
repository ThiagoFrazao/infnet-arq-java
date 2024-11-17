package br.infnet.thiagoarqjava.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UriUtils {

    @Value("${server.servlet.contextPath}")
    private String serverPath;

    @Value("${server.port}")
    private String serverPort;

    private static final String URI_TEMPLATE = "%s:%s/%s/%s";

    public URI crirUriNovoRecurso(String controllerPath, Long idNovoRecurso) throws URISyntaxException {
        return new URI(URI_TEMPLATE.formatted(this.serverPath, this.serverPort, controllerPath, idNovoRecurso));
    }
}
