package org.example;

import org.example.dao.UsuarioDao;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:jdbcapp", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute("""
            CREATE TABLE usuario (id IDENTITY PRIMARY KEY, 
                                  nome VARCHAR(100) NOT NULL, 
                                  email VARCHAR (100) NOT NULL UNIQUE)
            """);
        Scanner sc = new Scanner(System.in);
        UsuarioDao usuarioDao = new UsuarioDao(connection);
        while (true) {
            System.out.println("""
                    Menu:
                      1 - Adicionar Usuário
                      2 - Listar Usuários Cadastrados
                      3 - Atualizar Usuário
                      0 - Sair
                    """);
            System.out.print("Opção: ");
            int opcao = Integer.parseInt(sc.nextLine());
            switch (opcao) {
                case 1 -> {
                    System.out.print("Informe o nome do usuário:");
                    String nome = sc.nextLine();
                    System.out.print("Informe o email do usuário: ");
                    String email = sc.nextLine();
                    Usuario usuario = new Usuario(nome, email);
                    usuarioDao.salvar(usuario);
                }
                case 2 -> {
                    List<Usuario> usuarios = usuarioDao.listarTodos();
                    for (Usuario usuario: usuarios) {
                        System.out.println("Id: " + usuario.getId());
                        System.out.println("Nome: " + usuario.getNome());
                        System.out.println("Email: " + usuario.getEmail());
                        System.out.println("----------------------------");
                    }
                }
                case 3 -> {
                    System.out.println("Digite o id do usuário a ser alterado: ");
                    Long id = Long.parseLong(sc.nextLine());
                    Usuario usuario = usuarioDao.buscarPorId(id);
                    if (usuario == null) {
                        System.out.println("Usuário não encontrado");
                        break;
                    }
                    System.out.print("Informe o nome alterado do usuário:");
                    String nome = sc.nextLine();
                    System.out.print("Informe o email alterado do usuário: ");
                    String email = sc.nextLine();
                    if (!nome.isEmpty())
                        usuario.setNome(nome);
                    if (!email.isEmpty())
                        usuario.setEmail(email);
                    usuarioDao.atualizar(usuario);
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }
    }
}