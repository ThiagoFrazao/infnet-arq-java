package br.infnet.thiagoarqjava.repository;

import br.infnet.thiagoarqjava.domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
