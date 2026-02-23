package br.edu.ifpb.model;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_compra")
public class ItemCompra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Compra compra;

    @ManyToOne(optional=false)
    private Produto produto;

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable=false)
    private int quantidade;

    public ItemCompra() {}
    public ItemCompra(Compra compra, Produto produto, BigDecimal precoUnitario, int quantidade) {
        this.compra = compra;
        this.produto = produto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public Compra getCompra() { return compra; }
    public Produto getProduto() { return produto; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public int getQuantidade() { return quantidade; }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}

