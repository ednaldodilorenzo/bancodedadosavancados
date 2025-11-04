package br.edu.ifpb.dao;

import br.edu.ifpb.model.Disciplina;
import jakarta.persistence.EntityManager;

import java.util.Set;
import java.util.stream.Collectors;

public class DisciplinaDaoImpl extends BaseDao implements DisciplinaDao{

    public DisciplinaDaoImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Disciplina salvar(Disciplina disciplina) {
        if (disciplina.getId() == null) {
            em.persist(disciplina);
            return disciplina;
        }
        return em.merge(disciplina);
    }

    @Override
    public Set<Disciplina> listarTodas() {
        return em.createQuery("SELECT d FROM Disciplina d", Disciplina.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Disciplina buscarPorId(Long id) {
        return em.find(Disciplina.class, id);
    }
}
