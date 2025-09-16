package br.edu.ifpb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@DiscriminatorValue("BOLETO")
public class PagamentoBoleto extends Pagamento {
    @Column(length=64)
    private String codigoBarras;

    public PagamentoBoleto() {}
    public PagamentoBoleto(BigDecimal valor) {
        super(valor);
        this.codigoBarras = "836" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
    }

    public String getCodigoBarras() { return codigoBarras; }

    @Override public String detalhe() {
        return "Boleto (código=" + codigoBarras.substring(0, 15) + "...)";
    }
}

