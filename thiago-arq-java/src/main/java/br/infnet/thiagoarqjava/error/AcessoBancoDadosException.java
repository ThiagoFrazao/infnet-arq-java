package br.infnet.thiagoarqjava.error;

public class AcessoBancoDadosException extends RuntimeException {

    public AcessoBancoDadosException(String mensagem) {
        super(mensagem);
    }
}
