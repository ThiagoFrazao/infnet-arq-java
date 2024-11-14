package br.infnet.thiagoarqjava.events;

import br.infnet.thiagoarqjava.domain.Cliente;
import lombok.Getter;

import java.util.List;

@Getter
public class ClientesCarregadosEvent {

    private final List<Cliente> clientes;
    public ClientesCarregadosEvent(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
