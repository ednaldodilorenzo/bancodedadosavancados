package br.edu.ifpb;

import br.edu.ifpb.dao.ClienteDao;
import br.edu.ifpb.dao.CompraDao;
import br.edu.ifpb.dao.ProdutoDao;
import br.edu.ifpb.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;
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
            CompraDao compraDao = new CompraDao(mongoDatabase);
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
                        try (Jedis jedis = new Jedis("localhost", 6379)) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                String clientesCache = jedis.get("clientes:listar");
                                List<Cliente> clientes;
                                if (clientesCache == null) {
                                    System.out.println("Buscando no banco de dados...");
                                    clientes = clienteDao.buscarTodos();
                                    String clientesSerializados = mapper.writeValueAsString(clientes);
                                    jedis.setex("clientes:listar", 30, clientesSerializados);
                                } else {
                                    System.out.println("Buscando no cache...");
                                    clientes = mapper.readValue(clientesCache,
                                            mapper.getTypeFactory().constructCollectionType(List.class, Cliente.class));

                                }
                                clientes.forEach(cliente -> {
                                    System.out.println("ID: " + cliente.getId() + " Nome: " + cliente.getNome() + " CPF: " + cliente.getCpf());
                                    System.out.println("-----------------------");
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("Cadastrar Produto");
                        System.out.println("Informe o nome do produto: ");
                        String nomeProduto = new Scanner(System.in).nextLine();
                        System.out.println("Informe o valor do produto: ");
                        BigDecimal valorProduto = new Scanner(System.in).nextBigDecimal();
                        Produto produto = new Produto(nomeProduto, valorProduto);
                        produtoDao.salvar(produto);
                        System.out.println("Produto cadastrado com sucesso!");
                    }
                    case 4 -> {
                        System.out.println("Listar Produtos");
                        produtoDao.buscarTodos().forEach(produto -> {
                            System.out.println("ID: " + produto.getId() + " Nome: " + produto.getDescricao() + " Valor: " + produto.getValor());
                            System.out.println("-----------------------");
                        });
                    }
                    case 5 -> {
                        System.out.println("Digite o CPF do cliente: ");
                        String cpfCliente = new Scanner(System.in).nextLine();
                        Cliente cliente = clienteDao.buscarPorCpf(cpfCliente);
                        if (cliente == null) {
                            System.out.println("Cliente não encontrado!");
                            return;
                        }
                        Compra compra = new Compra();
                        while (true) {
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

                            compra.setCliente(cliente);
                            compra.addItem(new ItemCompra(produto, quantidade, produto.getValor()));
                        }
                        if (compra.getItens().isEmpty()) {
                            System.out.println("Nenhum item adicionado à compra. Operação cancelada.");
                            return;
                        }
                        System.out.println("Total da Compra (R$ " + compra.getTotal() + ")");
                        System.out.println("Pagamento (1=Boleto, 2=Cartão): ");
                        int tipoPagamento = new Scanner(System.in).nextInt();
                        switch (tipoPagamento) {
                            case 1 -> {
                                System.out.println("Compra realizada com Boleto.");
                            }
                            case 2 -> {
                                System.out.println("Informe o número do cartão: ");
                                String numeroCartao = new Scanner(System.in).nextLine();
                                System.out.println("Informe o nome impresso no cartão: ");
                                String nomeImpresso = new Scanner(System.in).nextLine();
                                compra.setPagamento(new PagamentoCartao(compra.getTotal(), numeroCartao, nomeImpresso));
                            }
                        }
                        compraDao.salvar(compra);
                        System.out.println("Compra realizada com sucesso!");
                    }
                    case 6 -> {
                        System.out.println("Digite o id do cliente: ");
                        String idCliente = new Scanner(System.in).nextLine();
                        Cliente cliente = clienteDao.buscarPorId(idCliente);
                        if (cliente == null) {
                            System.out.println("Cliente não encontrado!");
                            return;
                        }
                        compraDao.buscarTodosPeloIdCliente(idCliente).forEach(c -> {
                            System.out.println("ID: " + c.getId() + " Data: " + c.getData() + " Total: " + c.getTotal());
                            System.out.println("-----------------------");
                        });
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } while (opcao != 0);
        }
    }
}