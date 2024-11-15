package br.infnet.thiagoarqjava.repository;

import br.infnet.thiagoarqjava.domain.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
}
