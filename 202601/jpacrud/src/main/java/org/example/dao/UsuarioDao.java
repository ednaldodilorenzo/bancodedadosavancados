package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.Usuario;

import java.util.List;

public class UsuarioDao {
    private EntityManager em;

    public UsuarioDao(EntityManager em) {
        this.em = em;
    }

    public void salvar(Usuario usuario) {
        EntityTransaction tx = this.em.getTransaction();
        tx.begin();
        this.em.persist(usuario);
        tx.commit();
    }

    public void excluir(Long id) {
        EntityTransaction tx = this.em.getTransaction();
        tx.begin();
        Usuario usuario = this.buscarPorId(id);
        if (usuario != null)
            this.em.remove(usuario);
        tx.commit();
    }

    public void atualizar(Usuario usuario) {
        EntityTransaction tx = this.em.getTransaction();
        tx.begin();
        this.em.merge(usuario);
        tx.commit();
    }

    public Usuario buscarPorId(Long id) {
        return this.em.find(Usuario.class, id);
    }

    public List<Usuario> listarTodos() {
        return this.em.createQuery("select u from Usuario u order by u.id", Usuario.class).getResultList();
    }
}
