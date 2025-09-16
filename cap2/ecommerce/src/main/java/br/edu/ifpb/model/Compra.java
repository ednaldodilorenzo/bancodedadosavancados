package br.edu.ifpb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "compras")
public class Compra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Cliente cliente;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemCompra> itens = new HashSet<>();

    @OneToOne(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento pagamento;

    @Column(nullable=false)
    private LocalDateTime criadaEm = LocalDateTime.now();

    @Column(nullable=false, precision = 19, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    public Compra() {}
    public Compra(Cliente cliente) { this.cliente = cliente; }

    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Set<ItemCompra> getItens() { return itens; }
    public Pagamento getPagamento() { return pagamento; }
    public LocalDateTime getCriadaEm() { return criadaEm; }
    public BigDecimal getTotal() { return total; }

    public void addItem(ItemCompra item) { this.itens.add(item); }
    public void setPagamento(Pagamento p) {
        this.pagamento = p;
        p.setCompra(this);
    }

    public void recalcularTotal() {
        this.total = itens.stream()
                .map(ItemCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (pagamento != null) pagamento.setValor(total);
    }

    public String resumo() {
        return "Compra{id=%d, cliente=%s (%s), criadaEm=%s}".formatted(
                id, cliente.getNome(), cliente.getId(), criadaEm);
    }
}

