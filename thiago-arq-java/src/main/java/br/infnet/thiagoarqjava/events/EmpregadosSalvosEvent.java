package br.infnet.thiagoarqjava.events;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.Empregado;
import lombok.Getter;

import java.util.List;

@Getter
public class EmpregadosSalvosEvent {

    private final List<Cliente> clientes;
    private final List<Empregado> empregados;

    public EmpregadosSalvosEvent(List<Cliente> clientes, List<Empregado> empregados) {
        this.clientes = clientes;
        this.empregados = empregados;
    }

}
