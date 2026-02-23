package br.edu.ifpb.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CARTAO")
public class PagamentoCartao extends Pagamento {
    @Column(nullable=false, length=120)
    private String nomeImpresso;

    @Column(nullable=false, length=19)
    private String numeroMascarado;

    public PagamentoCartao() {}
    public PagamentoCartao(BigDecimal valor, String nomeImpresso, String numero) {
        super(valor);
        this.nomeImpresso = nomeImpresso;
        this.numeroMascarado = mascarar(numero);
    }

    private String mascarar(String n) {
        String digits = n.replaceAll("\\D", "");
        if (digits.length() < 4) return "****";
        String last4 = digits.substring(digits.length() - 4);
        return "**** **** **** " + last4;
    }

    public String getNomeImpresso() { return nomeImpresso; }
    public String getNumeroMascarado() { return numeroMascarado; }

    @Override public String detalhe() {
        return "Cartão (" + numeroMascarado + ", " + nomeImpresso + ")";
    }
}

