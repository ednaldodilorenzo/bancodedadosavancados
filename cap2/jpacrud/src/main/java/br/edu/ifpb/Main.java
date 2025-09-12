package br.edu.ifpb;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (UserRepository repo = new UserRepository(); Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n[JPA] Menu:\n1) Criar\n2) Listar\n3) Buscar por ID\n4) Atualizar\n5) Excluir\n0) Sair");
                System.out.print("Opção: ");
                String op = sc.nextLine().trim();
                try {
                    switch (op) {
                        case "1" -> {
                            System.out.print("Nome: ");
                            String nome = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            User u = repo.save(new User(nome, email));
                            System.out.println("Criado: " + u);
                        }
                        case "2" -> repo.findAll().forEach(System.out::println);
                        case "3" -> {
                            System.out.print("ID: ");
                            long id = Long.parseLong(sc.nextLine());
                            System.out.println(repo.findById(id));
                        }
                        case "4" -> {
                            System.out.print("ID: ");
                            long id = Long.parseLong(sc.nextLine());
                            System.out.print("Novo nome: ");
                            String nome = sc.nextLine();
                            System.out.print("Novo email: ");
                            String email = sc.nextLine();
                            User u = repo.findById(id);
                            if (u == null) System.out.println("Não encontrado");
                            else {
                                u.setNome(nome);
                                u.setEmail(email);
                                System.out.println("Atualizado: " + repo.save(u));
                            }
                        }
                        case "5" -> {
                            System.out.print("ID: ");
                            long id = Long.parseLong(sc.nextLine());
                            System.out.println(repo.delete(id) ? "Excluído" : "Não encontrado");
                        }
                        case "0" -> {
                            return;
                        }
                        default -> System.out.println("Opção inválida.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }
}