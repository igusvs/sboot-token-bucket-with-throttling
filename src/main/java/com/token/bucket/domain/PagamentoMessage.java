package com.token.bucket.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PagamentoMessage {

    private String formaPagamento;
    private String operacao;
    private String documento;
    private String id;

    public PagamentoMessage() {

    }

    public List<PagamentoMessage> gerarListaPagamentos(){
        final var listaPagamentos = new ArrayList<PagamentoMessage>();
        for(int i = 0; i < 10; i++){
            final var item = new PagamentoMessage();
            item.setFormaPagamento(this.formaPagamento);
            item.setOperacao(this.operacao);
            item.setDocumento(this.documento);
            item.setId(UUID.randomUUID().toString());
            listaPagamentos.add(item);
        }

        return listaPagamentos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
