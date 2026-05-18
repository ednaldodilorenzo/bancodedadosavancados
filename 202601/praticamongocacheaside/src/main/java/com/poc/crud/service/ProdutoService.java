package com.poc.crud.service;

import com.poc.crud.dao.ProdutoDao;
import com.poc.crud.model.Produto;

public class ProdutoService {
    private final ProdutoDao produtoDao;

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public void salvar(Produto produto) {
        this.produtoDao.salvar(produto);
    }

    public Produto buscarPorId(String id) {
        return this.produtoDao.buscarPorId(id);
    }
}
