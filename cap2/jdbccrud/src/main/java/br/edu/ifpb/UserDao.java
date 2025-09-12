package br.edu.ifpb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements AutoCloseable {
    private final Connection conn;

    public UserDao() {
        try {
            this.conn = DriverManager.getConnection("jdbc:h2:mem:jdbcapp;DB_CLOSE_DELAY=-1", "sa", "");
            initSchema();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private void initSchema() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS usuarios(id IDENTITY PRIMARY KEY, nome VARCHAR(120) NOT NULL, email VARCHAR(180) NOT NULL UNIQUE)");
        }
    }

    public long create(User u) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO usuarios(nome,email) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    u.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("Falha ao inserir usuário");
    }

    public User findById(long id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT id, nome, email FROM usuarios WHERE id=?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getLong("id"), rs.getString("nome"), rs.getString("email"));
                }
                return null;
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, nome, email FROM usuarios ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3)));
        }
        return out;
    }

    public boolean update(User u) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE usuarios SET nome=?, email=? WHERE id=?")) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setLong(3, u.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(long id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM usuarios WHERE id=?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    @Override public void close() throws SQLException { conn.close(); }
}
