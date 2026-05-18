package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Funcionario;

import java.util.List;

public class FuncionarioDao {
    private final EntityManager em;

    public FuncionarioDao(EntityManager em) {
        this.em = em;
    }

    public void salvar(Funcionario f) {
        this.em.getTransaction().begin();
        this.em.persist(f);
        this.em.getTransaction().commit();
    }

    public List<Funcionario> getFuncionarios() {
        return this.em.createQuery("select f from Funcionario f", Funcionario.class).getResultList();
    }
}
