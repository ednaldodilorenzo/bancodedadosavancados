package br.edu.ifpb.dao;

import br.edu.ifpb.model.Compra;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CompraDao {
    private final MongoCollection<Compra> col;

    public CompraDao(MongoDatabase db) {
        this.col = db.getCollection("compras", Compra.class);
    }

    public void salvar(Compra compra) {
        this.col.insertOne(compra);
    }

    public List<Compra> buscarTodosPeloIdCliente(String idCliente) {
        return this.col.find(eq("cliente._id", new ObjectId(idCliente))).into(new ArrayList<>());
    }
}
