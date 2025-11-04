package br.edu.ifpb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Professor extends Funcionario {
    private String titulacao;

    @OneToMany(mappedBy = "professor")
    private Set<Disciplina> disciplinas = new HashSet<>();

    public Professor() {
        super();
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    public Professor(String nome, String cpf, String titulacao) {
        super(nome, cpf);
        this.titulacao = titulacao;
    }
}
