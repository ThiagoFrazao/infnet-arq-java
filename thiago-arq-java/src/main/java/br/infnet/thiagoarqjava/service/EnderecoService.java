package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Endereco;
import br.infnet.thiagoarqjava.error.AcessoApiExternaException;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class EnderecoService {

    private final EnderecoRepository repository;

    private static final String HOST = "https://viacep.com.br/ws";

    private final WebClient webClient;
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.repository = enderecoRepository;
        this.webClient = WebClient.builder().baseUrl(HOST).build();
    }

    public Endereco consultarEnderecoPorCep(Long cep) {
        final Endereco enderecoViaCep = this.webClient.get()
                .uri("/{cep}/json/", cep)
                .exchangeToMono(response -> response.bodyToMono(Endereco.class))
                .block();

        if(enderecoViaCep == null) {
            throw new AcessoApiExternaException(HOST, cep);
        } else {
            return enderecoViaCep;
        }
    }

    public Endereco salvarEndereco(Endereco endereco) {
        try {
            final Endereco enderecoCompleto = this.consultarEnderecoPorCep(Long.valueOf(endereco.getCep()));
            enderecoCompleto.setNumero(endereco.getNumero());
            return this.repository.save(enderecoCompleto);
        } catch (Exception e) {
            log.error("Falha ao salvar endereco {}", endereco, e);
            throw new AcessoBancoDadosException("Falha ao salvar novo endereco %s".formatted(endereco.getCep()));
        }
    }

}
