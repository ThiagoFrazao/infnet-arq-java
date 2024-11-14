package br.infnet.thiagoarqjava.service;

import br.infnet.thiagoarqjava.domain.Empregado;
import br.infnet.thiagoarqjava.error.AcessoBancoDadosException;
import br.infnet.thiagoarqjava.repository.EmpregadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmpregadoService {

    private final EmpregadoRepository repository;
    private final EnderecoService enderecoService;

    public EmpregadoService(EmpregadoRepository repository, EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    public List<Empregado> salvarEmpregados(List<Empregado> empregados) {
        try {
            List<Empregado> empregadosSalvos = new ArrayList<>();
            for(Empregado empregado : empregados) {
                Empregado empregadoSalvo = this.salvarEmpregado(empregado);
                if(empregadoSalvo != null) {
                    empregadosSalvos.add(empregadoSalvo);
                }
            }
            return empregadosSalvos;
        } catch (Exception e) {
            log.error("Falha ao salvar empregados {}", empregados, e);
            throw new AcessoBancoDadosException("Falha ao salvar empregados");
        }
    }

    private Empregado salvarEmpregado(Empregado empregado) {
        try {
            empregado.setEndereco(enderecoService.salvarEndereco(empregado.getEndereco()));
            return repository.save(empregado);
        } catch (Exception e){
            log.error("Falha ao salvar empregado {}", empregado, e);
            return null;
        }
    }

}
