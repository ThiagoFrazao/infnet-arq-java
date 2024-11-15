package br.infnet.thiagoarqjava.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TRANSACAO")
@Getter
@Setter
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Empregado vendedor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "filme_id", referencedColumnName = "id")
    private Loja loja;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Enumerated(EnumType.STRING)
    private StatusTransacao statusTransacao;

    @OneToMany
    @JoinColumn(name = "filme_id", referencedColumnName = "id")
    private Set<Filme> filmes;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private LocalDateTime dataFimEsperada;

    public void addFilme(Filme filme) {
        if(this.filmes == null) {
            this.filmes = new HashSet<>();
        }
        this.filmes.add(filme);
    }

}
