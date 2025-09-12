package br.edu.ifpb;

public class User {
    private Long id;
    private String nome;
    private String email;

    public User() {}
    public User(Long id, String nome, String email) { this.id = id; this.nome = nome; this.email = email; }
    public User(String nome, String email) { this(null, nome, email); }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "User{id=%d, nome='%s', email='%s'}".formatted(id, nome, email);
    }
}