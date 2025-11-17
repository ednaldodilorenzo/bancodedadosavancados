package br.edu.ifpb.model;

public class Usuario {
    private String id;
    private String nome;
    private String papel;

    public Usuario() {
    }

    public Usuario(String id, String nome, String papel) {
        this.id = id;
        this.nome = nome;
        this.papel = papel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", papel='" + papel + '\'' +
                '}';
    }
}
