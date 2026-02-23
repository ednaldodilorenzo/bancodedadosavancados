package br.edu.ifpb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String descricao;

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal valor;

    public Produto() {}
    public Produto(String descricao, BigDecimal valor) { this.descricao = descricao; this.valor = valor; }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    @Override public String toString() {
        return "Produto{id=%d, desc='%s', valor=%s}".formatted(id, descricao, valor);
    }
}

