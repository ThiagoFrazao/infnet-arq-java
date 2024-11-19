package br.infnet.thiagoarqjava.models;

import lombok.Data;

@Data
public class InformacoesViaCep {

    private String cep;

    private String logradouro;

    private Integer numero;

    private String complemento;

    private String unidade;

    private String bairro;

    private String localidade;

    private String uf;

    private String estado;

    private String regiao;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;
}
