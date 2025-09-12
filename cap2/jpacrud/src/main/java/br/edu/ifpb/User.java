package br.edu.ifpb;


import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120)
    private String nome;

    @Column(nullable=false, length=180, unique = true)
    private String email;

    public User() {}
    public User(String nome, String email) { this.nome = nome; this.email = email; }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "User{id=%d, nome='%s', email='%s'}".formatted(id, nome, email);
    }
}