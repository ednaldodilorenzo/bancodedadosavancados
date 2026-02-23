package org.example.dao;

import org.example.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final Connection connection;

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Usuario usuario) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("""
                INSERT INTO usuario (NOME, EMAIL) VALUES (?, ?)
            """);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT id, nome, email FROM usuario ORDER BY id");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public Usuario buscarPorId(Long id) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("""
                SELECT id, nome, email FROM usuario WHERE id = ? 
            """);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getLong(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void atualizar(Usuario usuario) {
         try {
            PreparedStatement statement = this.connection.prepareStatement("""
                UPDATE usuario SET
                  nome = ?,
                  email = ?
                WHERE id = ?
            """);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setLong(3, usuario.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void apagar(Long id) {

    }
}
