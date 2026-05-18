package com.poc.crud.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.poc.crud.model.Produto;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class MongoProdutoDao implements ProdutoDao {

    private final MongoCollection<Document> produtoCollection;

    public MongoProdutoDao(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("praticamongocacheaside");
        this.produtoCollection = database.getCollection("produtos");
    }

    @Override
    public void salvar(Produto produto) {
        Document document = new Document("nome", produto.getNome()).append("categoria", produto.getCategoria()).append("preco", produto.getPreco());
        produtoCollection.insertOne(document);
    }

    @Override
    public Produto buscarPorId(String id) {
        // Note: You should also add a filter for the ID, otherwise it always returns the first document
        var document = this.produtoCollection.find(Filters.eq("_id", new ObjectId(id))).first();

        return (document != null) ? new Produto(
                document.getObjectId("_id").toString(),
                document.getString("nome"),
                document.getString("categoria"),
                document.get("preco", org.bson.types.Decimal128.class).bigDecimalValue()) : null;
    }

}
