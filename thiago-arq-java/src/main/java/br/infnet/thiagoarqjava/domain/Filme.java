package br.infnet.thiagoarqjava.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FILME")
@Getter
@Setter
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String diretor;

    private boolean nomeadoOscar;

    protected String nome;

    protected String descricao;

    protected StatusProduto status;

    protected Categoria categoria;

    protected int qntDisponivel;

    protected int qntTotal;

}
