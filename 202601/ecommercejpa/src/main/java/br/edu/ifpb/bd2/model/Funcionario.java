package br.edu.ifpb.bd2.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("F")
public class Funcionario extends Pessoa {
    @Column(nullable = false, unique = true)
    private String matricula;
    private String cargo;
}
