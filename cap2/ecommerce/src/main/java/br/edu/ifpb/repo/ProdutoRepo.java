package br.edu.ifpb.repo;


import br.edu.ifpb.model.Produto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProdutoRepo extends BaseRepo {
    public ProdutoRepo(EntityManager em) { super(em); }

    public Produto save(Produto p) {
        if (p.getId() == null) { em.persist(p); return p; }
        return em.merge(p);
    }

    public Produto findById(Long id) { return em.find(Produto.class, id); }

    public List<Produto> findAll() {
        return em.createQuery("select p from Produto p order by p.id", Produto.class).getResultList();
    }
}

