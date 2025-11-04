package br.edu.ifpb.dao;

import br.edu.ifpb.model.Disciplina;

import java.util.Set;

public interface DisciplinaDao {
    Disciplina salvar(Disciplina disciplina);

    Set<Disciplina> listarTodas();

    Disciplina buscarPorId(Long id);
}
