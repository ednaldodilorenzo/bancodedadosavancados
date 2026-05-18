package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public class Funcionario extends Usuario {
    private String matricula;
    @ManyToMany
    @JoinTable(name = "usuario_papel", joinColumns = @JoinColumn(name = "papel_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Papel> papeis;

}
