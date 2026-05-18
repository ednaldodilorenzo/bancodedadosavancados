package com.poc.crud.dao;

import com.poc.crud.model.Produto;

public interface ProdutoDao {
    void salvar(Produto produto);

    Produto buscarPorId(String id);
}
