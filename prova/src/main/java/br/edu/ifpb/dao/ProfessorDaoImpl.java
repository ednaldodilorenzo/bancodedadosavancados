package br.edu.ifpb.dao;

import br.edu.ifpb.model.Professor;
import jakarta.persistence.EntityManager;

public class ProfessorDaoImpl extends BaseDao implements ProfessorDao {
    public ProfessorDaoImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Professor salvar(Professor professor) {
        if (professor.getId() == null) {
            em.persist(professor);
            return professor;
        }
        return em.merge(professor);
    }
}
