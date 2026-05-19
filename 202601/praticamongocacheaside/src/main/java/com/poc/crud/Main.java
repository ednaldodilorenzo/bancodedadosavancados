package com.poc.crud;

import com.poc.crud.dao.ProdutoDaoFactory;
import com.poc.crud.model.Produto;
import com.poc.crud.service.ProdutoService;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var produtoService = new ProdutoService(ProdutoDaoFactory.getProdutoDao());
        produtoService.salvar(new Produto(null, "Teste", "Teste", new BigDecimal("27.5")));
        var produto = produtoService.buscarPorId("6a0ba36c26c4f05b45a48aeb");
        System.out.println(produto);
    }
}