package br.edu.ifpb;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import br.edu.ifpb.model.*;
import br.edu.ifpb.repo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ecommerce-pu");
        EntityManager em = emf.createEntityManager();

        ClienteRepo clienteRepo = new ClienteRepo(em);
        ProdutoRepo produtoRepo = new ProdutoRepo(em);
        CompraRepo compraRepo   = new CompraRepo(em);

        while (true) {
            System.out.println("""
                \n=== ECOMMERCE CONSOLE ===
                1) Cadastrar cliente
                2) Listar clientes
                3) Cadastrar produto
                4) Listar produtos
                5) Nova compra
                6) Listar compras de um cliente
                0) Sair
                """);
            System.out.print("Opção: ");
            String op = SC.nextLine().trim();
            try {
                switch (op) {
                    case "1" -> cadastrarCliente(clienteRepo);
                    case "2" -> listarClientes(clienteRepo);
                    case "3" -> cadastrarProduto(produtoRepo);
                    case "4" -> listarProdutos(produtoRepo);
                    case "5" -> novaCompra(clienteRepo, produtoRepo, compraRepo);
                    case "6" -> listarComprasCliente(compraRepo);
                    case "0" -> { em.close(); emf.close(); return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
    }

    private static void cadastrarCliente(ClienteRepo repo) {
        System.out.print("Nome: ");
        String nome = SC.nextLine();
        System.out.print("CPF: ");
        String cpf = SC.nextLine();
        repo.tx(() -> repo.save(new Cliente(nome, cpf)));
        System.out.println("Cliente cadastrado!");
    }

    private static void listarClientes(ClienteRepo repo) {
        repo.findAll().forEach(System.out::println);
    }

    private static void cadastrarProduto(ProdutoRepo repo) {
        System.out.print("Descrição: ");
        String descricao = SC.nextLine();
        System.out.print("Valor (ex 199.90): ");
        BigDecimal valor = new BigDecimal(SC.nextLine().replace(",", "."));
        repo.tx(() -> repo.save(new Produto(descricao, valor)));
        System.out.println("Produto cadastrado!");
    }

    private static void listarProdutos(ProdutoRepo repo) {
        repo.findAll().forEach(System.out::println);
    }

    private static void novaCompra(ClienteRepo clienteRepo, ProdutoRepo produtoRepo, CompraRepo compraRepo) {
        System.out.print("ID do cliente: ");
        long idCliente = Long.parseLong(SC.nextLine());
        Cliente cliente = clienteRepo.findById(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Compra compra = new Compra(cliente);
        while (true) {
            System.out.print("ID do produto (ou vazio para finalizar itens): ");
            String s = SC.nextLine().trim();
            if (s.isEmpty()) break;
            long idProduto = Long.parseLong(s);
            Produto p = produtoRepo.findById(idProduto);
            if (p == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            System.out.print("Quantidade: ");
            int qtd = Integer.parseInt(SC.nextLine());
            compra.addItem(new ItemCompra(compra, p, p.getValor(), qtd));
            System.out.println("Item adicionado: " + p.getDescricao() + " x " + qtd);
        }

        if (compra.getItens().isEmpty()) {
            System.out.println("Nenhum item informado. Compra cancelada.");
            return;
        }

        compra.recalcularTotal();
        System.out.println("Total da compra: R$ " + compra.getTotal());

        // pagamento
        Pagamento pagamento;
        System.out.println("Forma de pagamento: 1) Boleto  2) Cartão");
        String forma = SC.nextLine().trim();
        if ("2".equals(forma)) {
            System.out.print("Nome no cartão: ");
            String nome = SC.nextLine();
            System.out.print("Número do cartão (só dígitos, será mascarado): ");
            String num = SC.nextLine().replaceAll("\\s+", "");
            pagamento = new PagamentoCartao(compra.getTotal(), nome, num);
        } else {
            // default boleto
            pagamento = new PagamentoBoleto(compra.getTotal());
        }
        compra.setPagamento(pagamento);

        compraRepo.tx(() -> compraRepo.save(compra));
        System.out.println("Compra registrada! ID=" + compra.getId());
    }

    private static void listarComprasCliente(CompraRepo compraRepo) {
        System.out.print("ID do cliente: ");
        long idCliente = Long.parseLong(SC.nextLine());
        List<Compra> compras = compraRepo.findByCliente(idCliente);
        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra encontrada.");
            return;
        }
        for (Compra c : compras) {
            System.out.println("\n" + c.resumo());
            for (ItemCompra it : c.getItens()) {
                System.out.printf("  - %s x%d @ R$ %s%n",
                        it.getProduto().getDescricao(), it.getQuantidade(), it.getPrecoUnitario());
            }
            System.out.println("  Pagamento: " + c.getPagamento().detalhe());
            System.out.println("  Total: R$ " + c.getTotal());
        }
    }
}
