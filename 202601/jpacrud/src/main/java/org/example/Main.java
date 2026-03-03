package org.example;

import jakarta.persistence.*;
import org.example.dao.UsuarioDao;
import org.example.model.Status;
import org.example.model.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-app-pu");
    private static final EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {
        try {
            UsuarioDao usuarioDao = new UsuarioDao(em);
            while (true) {
                System.out.print("""
                        Menu:
                           1) Inserir Usuário
                           2) Listar Usuários Cadastrados
                           3) Buscar Usuário por ID
                           4) Atualizar Usuário
                           5) Excluir Usuário
                           0) Sair
                        opção: """);
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                int opcao = Integer.parseInt(bf.readLine());
                switch (opcao) {
                    case 1 -> {
                        Usuario usuario = new Usuario();
                        System.out.print("Digite o nome: ");
                        String name = bf.readLine();
                        usuario.setName(name);
                        System.out.print("Digite o CPF: ");
                        String cpf = bf.readLine();
                        usuario.setCpf(cpf);
                        System.out.print("Digite o username: ");
                        String username = bf.readLine();
                        usuario.setUsername(username);
                        System.out.print("Digite o status: ");
                        char status = bf.readLine().toLowerCase().charAt(0);
                        usuario.setStatus(status == 'a' ? Status.ATIVO
                                : Status.INATIVO);
                        usuarioDao.salvar(usuario);
                    }
                    case 2 -> {
                        List<Usuario> usuarios = usuarioDao.listarTodos();
                        usuarios.forEach(usuario -> System.out.println("Id: " + usuario.getId() + "\nNome: " + usuario.getName()));
                    }
                    case 3 -> {
                        System.out.print("Digite o id: ");
                        Long id = Long.parseLong(bf.readLine());
                        Usuario usuario = usuarioDao.buscarPorId(id);
                        if (usuario == null) {
                            System.out.println("Usuário não encontrado!");
                            continue;
                        }

                        System.out.println("Id: " + usuario.getId());
                        System.out.println("Nome: " + usuario.getName());
                        System.out.println("Username: " + usuario.getUsername());
                        System.out.println("CPF: " + usuario.getCpf());
                        System.out.println("Status: " + usuario.getStatus());
                    }
                    case 4 -> {
                        System.out.print("Digite o id: ");
                        Long id = Long.parseLong(bf.readLine());
                        Usuario usuario = usuarioDao.buscarPorId(id);
                        if (usuario == null) {
                            System.out.println("Usuário não encontrado!");
                            continue;
                        }

                        System.out.print("Digite o nome: ");
                        String name = bf.readLine();
                        if (!name.isBlank())
                            usuario.setName(name);
                        System.out.print("Digite o CPF: ");
                        String cpf = bf.readLine();
                        if (!cpf.isBlank())
                            usuario.setCpf(cpf);
                        System.out.print("Digite o username: ");
                        String username = bf.readLine();
                        if (!username.isBlank())
                            usuario.setUsername(username);
                        System.out.print("Digite o status: ");
                        char status = bf.readLine().toLowerCase().charAt(0);
                        if (status != ' ')
                            usuario.setStatus(status == 'a' ? Status.ATIVO
                                : Status.INATIVO);
                        usuarioDao.atualizar(usuario);
                    }
                    case 5 -> {
                        System.out.print("Digite o id: ");
                        Long id = Long.parseLong(bf.readLine());
                        Usuario usuario = usuarioDao.buscarPorId(id);
                        if (usuario == null) {
                            System.out.println("Usuário não encontrado!");
                            continue;
                        }
                        usuarioDao.excluir(id);
                    }
                    case 0 -> System.exit(0);
                    default -> System.out.println("Opção inválida");
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}