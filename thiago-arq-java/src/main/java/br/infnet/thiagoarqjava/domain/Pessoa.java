package br.infnet.thiagoarqjava.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Pessoa {

    protected String nome;
    protected Long cpf;
    protected String dataNascimento;
    protected String email;
    protected String telefone;



}
