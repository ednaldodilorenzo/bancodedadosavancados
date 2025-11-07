package br.edu.ifpb.dao;

import br.edu.ifpb.model.Compra;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CompraDao {
    private MongoCollection<Compra> col;

    public CompraDao(MongoDatabase db) {
        this.col = db.getCollection("compras", Compra.class);
    }

    public void salvar(Compra compra){
        this.col.insertOne(compra);
    }
}
