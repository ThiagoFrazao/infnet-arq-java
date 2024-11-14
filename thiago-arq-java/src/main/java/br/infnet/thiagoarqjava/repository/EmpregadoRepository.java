package br.infnet.thiagoarqjava.repository;

import br.infnet.thiagoarqjava.domain.Empregado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {
}
