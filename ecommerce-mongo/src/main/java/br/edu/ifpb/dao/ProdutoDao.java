package br.edu.ifpb.dao;

import br.edu.ifpb.model.Produto;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class ProdutoDao {
    private MongoCollection<Produto> col;

    public ProdutoDao(MongoDatabase db) {
        this.col = db.getCollection("produtos", Produto.class);
    }

    public Produto buscarPorId(String id){
        return this.col.find(eq("_id", id)).first();
    }
}
