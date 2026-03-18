package br.edu.ifpb.bd2;

import br.edu.ifpb.bd2.dao.ClienteDao;
import br.edu.ifpb.bd2.dao.PedidoDao;
import br.edu.ifpb.bd2.model.Cliente;
import br.edu.ifpb.bd2.model.Endereco;
import br.edu.ifpb.bd2.model.Pedido;
import br.edu.ifpb.bd2.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        EntityManager em;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-app-pu")) {
            em = emf.createEntityManager();

            ClienteDao clienteDao = new ClienteDao(em);
            PedidoDao pedidoDao = new PedidoDao(em);

            Cliente cliente = new Cliente();
            cliente.setNome("Pedro");
            cliente.setCpf("1234556666");
            Endereco endereco = new Endereco();
            endereco.setRua("Rua Pedro");
            endereco.setNumero("12345");
            endereco.setCep("12345");
            cliente.setEndereco(endereco);
            clienteDao.salvar(cliente);

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.adicionaProduto(new Produto("Pasta", 3, new BigDecimal(12)));
            pedido.adicionaProduto(new Produto("Suco", 4, new BigDecimal("5.4")));
            pedidoDao.salvar(pedido);

            List<Pedido> pedidos = pedidoDao.buscarTodos();
            pedidos.forEach(pedido1 ->
                    System.out.println(pedido1.getProdutos()));

        }
    }
}