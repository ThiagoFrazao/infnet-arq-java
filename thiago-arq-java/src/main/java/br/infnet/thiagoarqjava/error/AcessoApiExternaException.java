package br.infnet.thiagoarqjava.error;

public class AcessoApiExternaException extends RuntimeException {

    public AcessoApiExternaException(String host, Object... parametros) {
        super("Falha ao consultar API: host %s / Parametros: %s".formatted(host, parametros));

    }

}
