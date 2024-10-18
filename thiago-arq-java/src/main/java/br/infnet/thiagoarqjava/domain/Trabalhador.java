package br.infnet.thiagoarqjava.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Trabalhador {

    private String nome;
    private Long cpf;
    private String dataNascimento;
    private Double salario;



}
