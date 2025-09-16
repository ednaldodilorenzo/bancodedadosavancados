package br.edu.ifpb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pagamento")
public abstract class Pagamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "compra_id", unique = true)
    private Compra compra;

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(nullable=false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Pagamento() {}
    public Pagamento(BigDecimal valor) { this.valor = valor; }

    public Long getId() { return id; }
    public Compra getCompra() { return compra; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getCriadoEm() { return criadoEm; }

    public void setCompra(Compra compra) { this.compra = compra; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public abstract String detalhe();
}

