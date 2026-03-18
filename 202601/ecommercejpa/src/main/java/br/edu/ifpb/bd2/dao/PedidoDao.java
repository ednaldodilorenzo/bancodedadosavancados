package br.edu.ifpb.bd2.dao;

import br.edu.ifpb.bd2.model.Pedido;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PedidoDao implements Dao<Pedido, Long> {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public void salvar(Pedido valor) {
        this.em.getTransaction().begin();
        this.em.persist(valor);
        this.em.getTransaction().commit();
    }

    @Override
    public void atualizar(Pedido valor) {
        this.em.getTransaction().begin();
        this.em.merge(valor);
        this.em.getTransaction().commit();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return this.em.find(Pedido.class, id);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return this.em.createQuery("select p from Pedido p").getResultList();
    }
}
