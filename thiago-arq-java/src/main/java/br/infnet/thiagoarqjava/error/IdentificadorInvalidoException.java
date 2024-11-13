package br.infnet.thiagoarqjava.error;

public class IdentificadorInvalidoException extends RuntimeException {

    public IdentificadorInvalidoException(Long identificador, String tabela) {
        super("Não foi possivel encontrar o identificador %s na tabela %s".formatted(identificador, tabela));
    }

}
