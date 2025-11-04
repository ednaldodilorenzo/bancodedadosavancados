package br.edu.ifpb.dao;

import br.edu.ifpb.model.Aluno;

import java.util.Set;

public interface AlunoDao {
    Aluno salvar(Aluno aluno);

    Set<Aluno> buscarPorDisciplina(Long idDisciplina);

    Aluno buscarPorId(Long id);
}
