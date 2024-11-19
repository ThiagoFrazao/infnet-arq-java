package br.infnet.thiagoarqjava.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {


    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<ErrorResponseDTO> tratarErroGenerico(Exception e) {
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e));
    }

    @ExceptionHandler(AcessoBancoDadosException.class)
    public @ResponseBody ResponseEntity<ErrorResponseDTO> tratarAcessoBancoDados(AcessoBancoDadosException e) {
        log.debug("Falha AcessoBancoDadosException capturada");
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage(),
                "Não foi possível acessar um dos nossos sistemas internos no momento."));
    }

    @ExceptionHandler(IdentificadorInvalidoException.class)
    public @ResponseBody ResponseEntity<ErrorResponseDTO> tratarIdentificadorInvalido(IdentificadorInvalidoException e) {
        log.debug("Falha IdentificadorInvalidoException capturada");
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage(),
                "Um dos nossos sistemas internos se encontra com problema no momento."));
    }

    @ExceptionHandler(AcessoApiExternaException.class)
    public @ResponseBody ResponseEntity<ErrorResponseDTO> tratarAcessoApiExterna(AcessoApiExternaException e) {
        log.debug("Falha AcessoApiExternaException capturada");
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage(),
                "Não foi possível contactar um dos nossos sistemas externos no momento."));
    }

}
