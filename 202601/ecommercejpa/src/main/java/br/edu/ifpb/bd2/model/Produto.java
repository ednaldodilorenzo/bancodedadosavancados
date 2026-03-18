package br.edu.ifpb.bd2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor = BigDecimal.ZERO;

    private Integer quantidade = 0;

    private String nome;

    public Produto(
            String nome, Integer quantidade, BigDecimal valor
    ) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public BigDecimal getTotal() {
        if (valor == null || quantidade == null) {
            return BigDecimal.ZERO;
        }
        return this.valor.multiply(BigDecimal.valueOf(quantidade));
    }
}
