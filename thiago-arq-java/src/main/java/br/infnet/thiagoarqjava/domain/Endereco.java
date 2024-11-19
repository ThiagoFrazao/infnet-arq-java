package br.infnet.thiagoarqjava.domain;

import br.infnet.thiagoarqjava.models.InformacoesViaCep;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ENDERECO")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    private String complemento;

    private String unidade;

    private String bairro;

    private String localidade;

    private String uf;

    private String estado;

    public Endereco(InformacoesViaCep enderecoViaCep) {
        this.cep = enderecoViaCep.getCep();
        this.logradouro = enderecoViaCep.getLogradouro();
        this.numero = enderecoViaCep.getNumero();
        this.complemento = enderecoViaCep.getComplemento();
        this.unidade = enderecoViaCep.getUnidade();
        this.bairro = enderecoViaCep.getBairro();
        this.localidade = enderecoViaCep.getLocalidade();
        this.uf = enderecoViaCep.getUf();
        this.estado = enderecoViaCep.getEstado();
    }
}
