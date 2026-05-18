package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Papel;

public class PapelDao {
    private EntityManager em;

    public PapelDao(EntityManager em) {
        this.em = em;
    }

    public Papel salvar(Papel papel) {
        this.em.getTransaction().begin();
        this.em.persist(papel);
        this.em.getTransaction().commit();
        return papel;
    }
}
