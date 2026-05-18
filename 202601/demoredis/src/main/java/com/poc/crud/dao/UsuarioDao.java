package com.poc.crud.dao;

import com.poc.crud.model.Usuario;

public class UsuarioDao {

    public Usuario findById(String id) {
        return new Usuario(id, "Usuario " + id, "usuario"+ id + "@teste.com");
    }
}
