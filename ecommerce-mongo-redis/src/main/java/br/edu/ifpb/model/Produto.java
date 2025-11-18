package br.edu.ifpb.model;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

public final class Produto {
    private ObjectId id;
    private String descricao;
    private BigDecimal valor;

    public Produto(){}

    public Produto(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
