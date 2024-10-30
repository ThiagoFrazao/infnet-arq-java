package br.infnet.thiagoarqjava.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Produto {

    protected String nome;

    protected String descricao;

    protected StatusProduto status;

    protected Categoria categoria;

    protected int qntDisponivel;

    protected int qntTotal;

}