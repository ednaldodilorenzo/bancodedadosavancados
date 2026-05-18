package com.poc.crud.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ProdutoDaoFactory {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static ProdutoDao produtoDao = null;

    public static ProdutoDao getProdutoDao() {

        if (produtoDao == null) {
            produtoDao = new MongoProdutoDao(mongoClient);
        }

        return produtoDao;
    }
}
