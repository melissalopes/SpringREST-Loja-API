package com.lojaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Compra;
import com.lojaapi.model.Estoque;
import com.lojaapi.model.ItensDaCompra;
import com.lojaapi.repository.CompraRepository;
import com.lojaapi.repository.EstoqueRepository;
import com.lojaapi.repository.ProdutoRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository repository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	public Compra salvar(Compra compra) {			
		
		for(ItensDaCompra salvaItens : compra.getItensDaCompra()) {	
			Long produtoCodigo = produtoRepository.findById(salvaItens.getProduto().getCodigo()).get().getCodigo();			
			Estoque estoque = estoqueRepository.findProdutoByCodigo(produtoCodigo);	
			estoque.setQuantidade(salvaItens.getQuantidade() + estoque.getQuantidade());
			estoqueRepository.save(estoque);
		}
		return repository.save(compra);
	}
}
