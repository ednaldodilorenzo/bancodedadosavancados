package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="usuarios")
@Data
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nome")
    private String name;
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;
    private String username;
    @Enumerated(EnumType.STRING)
    private Status status;
}
