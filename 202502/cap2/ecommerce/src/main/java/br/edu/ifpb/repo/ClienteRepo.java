package br.edu.ifpb.repo;


import br.edu.ifpb.model.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteRepo extends BaseRepo {
    public ClienteRepo(EntityManager em) { super(em); }

    public Cliente save(Cliente c) {
        if (c.getId() == null) { em.persist(c); return c; }
        return em.merge(c);
    }

    public Cliente findById(Long id) { return em.find(Cliente.class, id); }

    public List<Cliente> findAll() {
        return em.createQuery("select c from Cliente c order by c.id", Cliente.class).getResultList();
    }
}

