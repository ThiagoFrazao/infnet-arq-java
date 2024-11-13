package br.infnet.thiagoarqjava.repository;

import br.infnet.thiagoarqjava.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findClienteById(Long clienteId);
}
