package br.edu.ifpb.model;

import jakarta.persistence.Entity;

@Entity
public class TecnicoAdministrativo extends Funcionario {
    private String cargo;

    public TecnicoAdministrativo() {
        super();
    }

    public TecnicoAdministrativo(String nome, String cpf, String cargo) {
        super(nome, cpf);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
