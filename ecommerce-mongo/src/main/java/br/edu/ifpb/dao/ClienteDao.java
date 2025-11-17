package br.edu.ifpb.dao;

import br.edu.ifpb.model.Cliente;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClienteDao {
    private final MongoCollection<Cliente> col;

    public ClienteDao(MongoDatabase db) {
        this.col = db.getCollection("clientes", Cliente.class);
    }

    public void salvar(Cliente cliente) {
        this.col.insertOne(cliente);
    }

    public Cliente buscarPorId(String id) {
        return this.col.find(eq("_id", new ObjectId(id))).first();
    }

    public Cliente buscarPorCpf(String cpf) {
        return this.col.find(eq("cpf", cpf)).first();
    }

    public List<Cliente> buscarTodos() {
        return this.col.find().into(new ArrayList<>());
    }
}
