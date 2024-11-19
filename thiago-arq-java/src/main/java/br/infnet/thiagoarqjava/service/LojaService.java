package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Cliente;
import br.infnet.thiagoarqjava.domain.Loja;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.error.IdentificadorInvalidoException;
import br.infnet.thiagoarqjava.repository.LojaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LojaService {

    private final LojaRepository lojaRepository;
    private final EnderecoService enderecoService;

    public LojaService(LojaRepository lojaRepository, EnderecoService enderecoService) {
        this.lojaRepository = lojaRepository;
        this.enderecoService = enderecoService;
    }

    public List<Loja> salvarLojas(List<Loja> lojas) {
        try {
            List<Loja> novasLojas = new ArrayList<>();
            for (Loja loja : lojas) {
                Loja novaLoja = this.salvarLoja(loja);
                if(novaLoja != null) {
                    novasLojas.add(novaLoja);
                }
            }
            return novasLojas;
        } catch (Exception e){
            log.error("Falha ao salvar lojas ");
            throw new AcessoBancoDadosException("Falha ao salvar lojas");
        }
    }

    public Loja salvarLoja(Loja loja) {
        try {
            if(loja.getEndereco().getId() == null) {
                loja.setEndereco(this.enderecoService.salvarEndereco(loja.getEndereco()));
            }
            return this.lojaRepository.save(loja);
        } catch (Exception e) {
            log.error("Erro ao salvar loja. Loja nao sera salva.", e);
            return null;
        }
    }


    public List<Loja> recuperarTodasLojas() {
        try {
            return this.lojaRepository.findAll();
        } catch (Exception e) {
            log.error("Erro ao recuperar todas lojas.", e);
            throw new AcessoBancoDadosException("Falha ao recuperar todas lojas");
        }
    }

    public Loja recuperarLojaPorId(Long id) {
        try {
            return this.lojaRepository.findById(id).orElseThrow(() -> new IdentificadorInvalidoException(id, "LOJA"));
        } catch (IdentificadorInvalidoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao recuperar loja {}", id, e);
            throw new AcessoBancoDadosException("Não foi possivel recuperar loja com ID: %s".formatted(id));
        }
    }
}
