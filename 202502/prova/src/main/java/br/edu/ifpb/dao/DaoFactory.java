package br.edu.ifpb.dao;

import jakarta.persistence.EntityManager;

public class DaoFactory {
    public static AlunoDao criarAlunoDao(EntityManager em) {
        return new AlunoDaoImpl(em);
    }

    public static ProfessorDao criarProfessorDao(EntityManager em) {
        return new ProfessorDaoImpl(em);
    }

    public static DisciplinaDao criarDisciplinaDao(EntityManager em) {
        return new DisciplinaDaoImpl(em);
    }
}
