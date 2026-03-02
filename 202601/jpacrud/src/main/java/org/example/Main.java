package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
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
                    case 1 -> System.out.println("Salvar usuário");
                    case 2 -> System.out.println("Listar Usuários");
                    case 3 -> System.out.println("Buscar usuário por id");
                    case 4 -> System.out.println("Atualizar usuário");
                    case 5 -> System.out.println("Excluir usuário");
                    case 0 -> {
                        break;
                    }
                    default ->
                        System.out.println("Opção inválida");
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}