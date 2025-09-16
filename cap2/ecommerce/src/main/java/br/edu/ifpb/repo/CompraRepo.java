package br.edu.ifpb.repo;

import br.edu.ifpb.model.Compra;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CompraRepo extends BaseRepo {
    public CompraRepo(EntityManager em) { super(em); }

    public Compra save(Compra c) {
        if (c.getId() == null) { em.persist(c); return c; }
        return em.merge(c);
    }

    public Compra findById(Long id) { return em.find(Compra.class, id); }

    public List<Compra> findByCliente(Long idCliente) {
        return em.createQuery("""
                select c from Compra c
                left join fetch c.itens i
                left join fetch c.pagamento
                where c.cliente.id = :id
                """, Compra.class)
                .setParameter("id", idCliente)
                .getResultList();
    }
}

