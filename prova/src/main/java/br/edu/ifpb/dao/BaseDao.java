package br.edu.ifpb.dao;

import jakarta.persistence.EntityManager;

public abstract class BaseDao {
    protected final EntityManager em;

    protected BaseDao(EntityManager em) {
        this.em = em;
    }
}
