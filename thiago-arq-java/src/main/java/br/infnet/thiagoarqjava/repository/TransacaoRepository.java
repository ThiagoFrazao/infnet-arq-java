package br.infnet.thiagoarqjava.repository;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.TipoTransacao;
import br.infnet.thiagoarqjava.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByClienteAndTipo(Cliente cliente, TipoTransacao tipo);


    List<Transacao> findAllByClienteId(Long clienteId);
}
