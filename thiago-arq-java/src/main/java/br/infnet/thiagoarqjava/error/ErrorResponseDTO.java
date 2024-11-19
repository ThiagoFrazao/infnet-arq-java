package br.infnet.thiagoarqjava.error;

import lombok.Getter;

@Getter
public class ErrorResponseDTO {

    private final String msgTecnica;

    private final String msgCliente;

    public ErrorResponseDTO(Throwable error) {
        this.msgTecnica = error.getMessage();
        this.msgCliente = "Houveu um erro ao realizar seu pedido. Tente novamente em alguns instantes ou entre em contato para melhor atendimento.";
    }

    public ErrorResponseDTO(String msgTecnica, String msgCliente) {
        this.msgTecnica = msgTecnica;
        this.msgCliente = msgCliente;
    }

}
