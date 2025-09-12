package br.edu.ifpb;

import jakarta.persistence.*;

import java.util.List;

public class UserRepository implements AutoCloseable {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-app-pu");
    private final EntityManager em = emf.createEntityManager();

    public User save(User u) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (u.getId() == null) {
            em.persist(u);
            em.detach(u);
            u.setEmail("teste@teste.com");
        }
        else u = em.merge(u);
        tx.commit();
        return u;
    }

    public User findById(Long id) { return em.find(User.class, id); }

    public List<User> findAll() {
        return em.createQuery("select u from User u order by u.id", User.class).getResultList();
    }

    public boolean delete(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User u = em.find(User.class, id);
        if (u != null) em.remove(u);
        tx.commit();
        return u != null;
    }

    @Override public void close() {
        em.close();
        emf.close();
    }
}