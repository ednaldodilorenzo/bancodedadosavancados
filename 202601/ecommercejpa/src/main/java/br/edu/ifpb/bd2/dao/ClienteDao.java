package br.edu.ifpb.bd2.dao;

import br.edu.ifpb.bd2.model.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteDao implements Dao<Cliente, Long> {
    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void salvar(Cliente cliente) {
        this.em.getTransaction().begin();
        this.em.persist(cliente);
        this.em.getTransaction().commit();
    }

    public void atualizar(Cliente cliente) {
        this.em.getTransaction().begin();
        this.em.merge(cliente);
        this.em.getTransaction().commit();
    }

    public Cliente buscarPorId(Long id) {
        return this.em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        return this.em.createQuery("select c from Cliente c").getResultList();
    }
}
