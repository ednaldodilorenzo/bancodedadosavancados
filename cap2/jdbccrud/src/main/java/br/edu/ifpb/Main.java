package br.edu.ifpb;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (UserDao dao = new UserDao(); Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("[JDBC] Menu:\n1) Criar\n2) Listar\n3) Buscar por ID\n4) Atualizar\n5) Excluir\n0) Sair");
                System.out.print("Opção: ");
                String op = sc.nextLine().trim();
                try {
                    switch (op) {
                        case "1" -> {
                            System.out.print("Nome: "); String nome = sc.nextLine();
                            System.out.print("Email: "); String email = sc.nextLine();
                            long id = dao.create(new User(nome, email));
                            System.out.println("Criado ID = " + id);
                        }
                        case "2" -> dao.findAll().forEach(System.out::println);
                        case "3" -> {
                            System.out.print("ID: "); long id = Long.parseLong(sc.nextLine());
                            System.out.println(dao.findById(id));
                        }
                        case "4" -> {
                            System.out.print("ID: "); long id = Long.parseLong(sc.nextLine());
                            System.out.print("Novo nome: "); String nome = sc.nextLine();
                            System.out.print("Novo email: "); String email = sc.nextLine();
                            boolean ok = dao.update(new User(id, nome, email));
                            System.out.println(ok ? "Atualizado" : "Não encontrado");
                        }
                        case "5" -> {
                            System.out.print("ID: "); long id = Long.parseLong(sc.nextLine());
                            System.out.println(dao.delete(id) ? "Excluído" : "Não encontrado");
                        }
                        case "0" -> { return; }
                        default -> System.out.println("Opção inválida.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }
}