package br.edu.ifpb.dao;

import br.edu.ifpb.model.Aluno;
import jakarta.persistence.EntityManager;

import java.util.Set;

public class AlunoDaoImpl extends BaseDao implements AlunoDao {
    public AlunoDaoImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        if (aluno.getId() == null) {
            em.persist(aluno);
            return aluno;
        }
        return em.merge(aluno);
    }

    @Override
    public Set<Aluno> buscarPorDisciplina(Long idDisciplina) {
        return em.createQuery("SELECT a FROM Aluno a JOIN a.disciplinas d WHERE d.id = :idDisciplina", Aluno.class)
                .setParameter("idDisciplina", idDisciplina)
                .getResultStream().collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public Aluno buscarPorId(Long id) {
        return em.find(Aluno.class, id);
    }
}
