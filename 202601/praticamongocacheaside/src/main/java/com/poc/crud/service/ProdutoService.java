package com.poc.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.crud.dao.ProdutoDao;
import com.poc.crud.model.Produto;
import redis.clients.jedis.Jedis;

public class ProdutoService {
    private final ProdutoDao produtoDao;
    private final Jedis jedis = new Jedis("localhost", 6379);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public void salvar(Produto produto) {
        this.produtoDao.salvar(produto);
    }

    public Produto buscarPorId(String id) throws JsonProcessingException {
        String chave = "produto:" + id;
        String cache = jedis.get(chave);
        if (cache != null) {
            return objectMapper.readValue(cache, Produto.class);
        }
        var produto = this.produtoDao.buscarPorId(id);
        jedis.setex(chave, 3600, objectMapper.writeValueAsString(produto));
        return produto;
    }
}
