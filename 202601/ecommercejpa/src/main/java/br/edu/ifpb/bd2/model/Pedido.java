package br.edu.ifpb.bd2.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    private BigDecimal total = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<Produto> produtos =   new ArrayList<>();

    public void adicionaProduto(Produto produto) {
        this.produtos.add(produto);
        this.total = this.total.add(produto.getTotal());
    }

    public void removeProduto(Produto produto) {
        this.produtos.remove(produto);
        this.total = this.total.subtract(produto.getTotal());
    }
}
