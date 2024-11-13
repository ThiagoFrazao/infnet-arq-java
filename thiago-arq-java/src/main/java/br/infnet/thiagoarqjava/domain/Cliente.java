package br.infnet.thiagoarqjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "CLIENTE", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "email"}))
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "cliente")
    private Set<Transacao> transacoes;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    protected Endereco endereco;

}
