package br.edu.ifpb.repo;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Supplier;

public abstract class BaseRepo {
    protected final EntityManager em;

    protected BaseRepo(EntityManager em) { this.em = em; }

    public void tx(Runnable r) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try { r.run(); tx.commit(); }
        catch (RuntimeException e) { if (tx.isActive()) tx.rollback(); throw e; }
    }

    public <T> T tx(Supplier<T> s) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try { T res = s.get(); tx.commit(); return res; }
        catch (RuntimeException e) { if (tx.isActive()) tx.rollback(); throw e; }
    }
}

