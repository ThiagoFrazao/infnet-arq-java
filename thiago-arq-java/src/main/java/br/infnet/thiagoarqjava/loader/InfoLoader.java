package br.infnet.thiagoarqjava.loader;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.Empregado;
import br.infnet.thiagoarqjava.domain.Filme;
import br.infnet.thiagoarqjava.domain.Loja;
import br.infnet.thiagoarqjava.domain.StatusTransacao;
import br.infnet.thiagoarqjava.domain.TipoTransacao;
import br.infnet.thiagoarqjava.domain.Transacao;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.service.ClienteService;
import br.infnet.thiagoarqjava.service.EmpregadoService;
import br.infnet.thiagoarqjava.service.FilmeService;
import br.infnet.thiagoarqjava.service.LojaService;
import br.infnet.thiagoarqjava.service.TransacaoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class InfoLoader {


    private static final String CLASSPATH_PREFIX = "classpath:loadfiles/";
    private static final String CLIENTE_FILE_NAME = "clientLoadFile.json";
    private static final String EMPREGADO_FILE_NAME = "empregadosLoadFile.json";
    private static final String LOJA_FILE_NAME = "lojaLoadFile.json";
    private static final String FILMES_FILE_NAME = "filmesLoadFile.json";

    private final ObjectMapper objectMapper;
    private final ResourceLoader loader;
    private final ClienteService clienteService;
    private final LojaService lojaService;
    private final EmpregadoService empregadoService;
    private final FilmeService filmesService;
    private final TransacaoService transacaoService;
    private final Random random;

    public InfoLoader(ResourceLoader resourceLoader,
                      ClienteService clienteService,
                      LojaService lojaService,
                      EmpregadoService empregadoService,
                      FilmeService filmesService,
                      TransacaoService transacaoService) {
        this.clienteService = clienteService;
        this.lojaService = lojaService;
        this.empregadoService = empregadoService;
        this.filmesService = filmesService;
        this.transacaoService = transacaoService;
        this.objectMapper = new ObjectMapper();
        this.loader = resourceLoader;
        this.random = new Random();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void carregarInformacoes() {
        try {
            log.debug("Iniciando carga dos arquivos na pasta: \"%s\"".formatted(CLASSPATH_PREFIX));
            final List<Cliente> clientes = this.carregarClientes();
            List<Loja> lojas = this.carregarLojas();
            List<Filme> filmes = this.carregarFilmes();
            final List<Empregado> empregados = this.carregarEmpregados(lojas);
            lojas = this.atualizarLojas(lojas, empregados);
            filmes = this.atualizarFilmes(filmes, lojas);
            this.criarTransacoes(clientes, empregados, lojas, filmes);
            log.debug("Carga de informacoes finalizada com sucesso.");
        } catch (Throwable e) {
            log.error("Falha durante carga dos arquivos. Talvez nem todas as tabelas tenham sido inicializadas. Finalize a aplicacao e inicie novamente.", e);
        }

    }

    private List<Filme> atualizarFilmes(List<Filme> filmes, List<Loja> lojas) {
        try {
            for(Filme filme : filmes) {
                filme.setLoja(lojas.get(random.nextInt(lojas.size())));
            }
            return this.filmesService.salvarFilmes(filmes);
        } catch (Exception e) {
            log.error("Falha ao atualizar filmes. Processo de criacao de dados finalizado.", e);
            throw new AcessoBancoDadosException("Falha ao atualizar filmes");
        }
    }

    private void criarTransacoes(List<Cliente> clientes, List<Empregado> empregados, List<Loja> lojas, List<Filme> filmes) {
        final LocalDateTime hoje = LocalDateTime.now();
        final List<Transacao> transacoes = new ArrayList<>();
        for(Empregado empregado : empregados) {
            Transacao transacao = new Transacao();
            transacao.setCliente(clientes.get(this.random.nextInt(clientes.size())));
            transacao.setVendedor(empregado);
            transacao.addFilme(filmes.get(this.random.nextInt(filmes.size())));
            transacao.setLoja(lojas.get(this.random.nextInt(lojas.size())));
            transacao.setDataInicio(hoje);
            transacao.setDataFim(null);
            transacao.setDataFimEsperada(hoje.plusDays(this.random.nextInt(this.random.nextInt(10))));
            transacao.setTipo(TipoTransacao.values()[this.random.nextInt(TipoTransacao.values().length)]);
            transacao.setStatusTransacao(StatusTransacao.ABERTA);
            transacoes.add(transacao);
        }
        this.transacaoService.salvarTransacoes(transacoes);
    }

    private List<Filme> carregarFilmes() {
        final Resource filmeResource = this.loader.getResource(CLASSPATH_PREFIX + FILMES_FILE_NAME);
        try(InputStream inputStream = filmeResource.getInputStream()) {
            final List<Filme> filmes = this.objectMapper.readValue(inputStream, new TypeReference<>() {});
            return this.filmesService.salvarFilmes(filmes);
        } catch (Exception e) {
            throw new AcessoBancoDadosException("Falha ao carregar filmes a partir do arquivo %s. Carga de informacoes sera encerrada."
                    .formatted(CLASSPATH_PREFIX + FILMES_FILE_NAME));
        }
    }

    private List<Loja> atualizarLojas(List<Loja> lojas, List<Empregado> empregados) {
        try {
            for(Loja loja : lojas) {
                for(Empregado empregado : empregados) {
                    if(loja.getId().equals(empregado.getLoja().getId())) {
                        loja.getEmpregados().add(empregado);
                    }
                }
            }
            return this.lojaService.salvarLojas(lojas);
        } catch (Exception e) {
            log.error("Falha ao atualizar lojas. Lojas seguiram sem empregados e filmes", e);
            return lojas;
        }
    }

    private List<Loja> carregarLojas() {
        final Resource lojasResource = this.loader.getResource(CLASSPATH_PREFIX + LOJA_FILE_NAME);
        try(InputStream inputStream = lojasResource.getInputStream()) {
            final List<Loja> lojas = this.objectMapper.readValue(inputStream, new TypeReference<>() {});
            return this.lojaService.salvarLojas(lojas);

        } catch (Exception e) {
            log.error("Erro ao carregar lojas", e);
            throw new AcessoBancoDadosException("Falha ao carregar lojas a partir do arquivo %s. Carga de informacoes sera encerrada."
                    .formatted(CLASSPATH_PREFIX + LOJA_FILE_NAME));
        }
    }

    public List<Empregado> carregarEmpregados(final List<Loja> lojas) {
        final Resource empregadoResource = this.loader.getResource(CLASSPATH_PREFIX + EMPREGADO_FILE_NAME);
        try(InputStream inputStream = empregadoResource.getInputStream()) {
            final List<Empregado> empregados = this.objectMapper.readValue(inputStream, new TypeReference<>() {});
            empregados.forEach(empregado -> {
                empregado.setLoja(lojas.get(random.nextInt(lojas.size())));
            });
            return this.empregadoService.salvarEmpregados(empregados);
        } catch (Exception e){
            log.error("Falha ao carregar empregados", e);
            throw new AcessoBancoDadosException("Falha ao carregar empregados a partir do arquivo %s. Carga de informacoes sera encerrada."
                    .formatted(CLASSPATH_PREFIX + EMPREGADO_FILE_NAME));
        }
    }

    private List<Cliente> carregarClientes() {
        final Resource clienteResource = this.loader.getResource(CLASSPATH_PREFIX + CLIENTE_FILE_NAME);
        try(InputStream inputStream = clienteResource.getInputStream()) {
            final List<Cliente> clientes = this.objectMapper.readValue(inputStream, new TypeReference<>() {});
            return this.clienteService.salvarClientes(clientes);
        } catch (Exception e) {
            log.error("Falha ao carregar clientes", e);
            throw new AcessoBancoDadosException("Falha ao carregar clientes a partir do arquivo %s. Carga de informacoes sera encerrada."
                    .formatted(CLASSPATH_PREFIX + CLIENTE_FILE_NAME));
        }
    }

}
