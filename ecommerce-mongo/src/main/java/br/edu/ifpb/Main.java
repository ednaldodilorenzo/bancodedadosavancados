package br.edu.ifpb;

import br.edu.ifpb.dao.ClienteDao;
import br.edu.ifpb.dao.ProdutoDao;
import br.edu.ifpb.model.Cliente;
import br.edu.ifpb.model.Compra;
import br.edu.ifpb.model.ItemCompra;
import br.edu.ifpb.model.Produto;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.math.BigDecimal;
import java.util.Scanner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
                        .codecRegistry(pojoCodecRegistry)
                        .build())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("ecommerce");

            ClienteDao clienteDao = new ClienteDao(mongoDatabase);
            ProdutoDao produtoDao = new ProdutoDao(mongoDatabase);
            int opcao = 0;
            do {
                System.out.println("""
                        1) Cadastrar Cliente
                        2) Listar Clientes
                        3) Cadastrar Produto
                        4) Listar Produtos
                        5) Nova Compra
                        6) Listar Compras de um Cliente
                        0) Sair
                        """);
                System.out.print("Opção: ");
                opcao = new Scanner(System.in).nextInt();
                switch (opcao) {
                    case 1 -> {
                        System.out.println("Informe o nome do cliente: ");
                        String nome = new Scanner(System.in).nextLine();
                        System.out.println("Informe o CPF do cliente: ");
                        String cpf = new Scanner(System.in).nextLine();
                        Cliente cliente = new Cliente(nome, cpf);
                        clienteDao.salvar(cliente);
                        System.out.println("Cliente cadastrado com sucesso!");
                        break;
                    }
                    case 2 -> {
                        clienteDao.buscarTodos().forEach(cliente -> {
                            System.out.println("ID: " + cliente.getId() + " Nome: " + cliente.getNome() + " CPF: " + cliente.getCpf());
                            System.out.println("-----------------------");
                        });
                    }
                    case 3 -> System.out.println("Cadastrar Produto");
                    case 4 -> System.out.println("Listar Produtos");
                    case 5 -> {
                        System.out.println("Digite o CPF do cliente: ");
                        String cpfCliente = new Scanner(System.in).nextLine();
                        Cliente cliente = clienteDao.buscarPorCpf(cpfCliente);
                        if (cliente == null) {
                            System.out.println("Cliente não encontrado!");
                            return;
                        }

                        while(true) {
                            System.out.println("Digite o ID do produto (ou vazio para finalizar): ");
                            String idProduto = new Scanner(System.in).nextLine();
                            if (idProduto.isBlank()) {
                                break;
                            }
                            Produto produto = produtoDao.buscarPorId(idProduto);
                            if (produto == null) {
                                System.out.println("Produto não encontrado!");
                                continue;
                            }
                            System.out.println("Quantidade: ");
                            int quantidade = new Scanner(System.in).nextInt();
                            Compra compra = new Compra();
                            compra.setCliente(cliente);
                            compra.addItem(new ItemCompra(produto, quantidade, produto.getValor()));
                        }
                    }
                    case 6 -> System.out.println("Listar Compras de um Cliente");
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } while (opcao != 0);
        }
    }
}