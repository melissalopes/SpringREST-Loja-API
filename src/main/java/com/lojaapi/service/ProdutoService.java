package com.lojaapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Produto;
import com.lojaapi.repository.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public Page<Produto> findPage(Pageable pageable){
		return repository.findAll(pageable);
	}
	
	public Produto buscarProdutoPeloCodigo(Long codigo) {
		Optional<Produto> produtoSalvo = repository.findById(codigo);
		retornaErroCasoEmpty(produtoSalvo);
		Produto produtoNovo = produtoSalvo.get();
		return produtoNovo;
	}
	
	public List<Produto> buscarProdutoPeloNome(String nome) {
		List<Produto> produtoSalvo = repository.findByNome(nome);
		if(produtoSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produtoSalvo;
	}

	
	public Produto atualizar(Long codigo, Produto produto) {
		Optional<Produto> produtoSalvo = repository.findById(codigo);
		retornaErroCasoEmpty(produtoSalvo);
		Produto produtoNovo = produtoSalvo.get();
		BeanUtils.copyProperties(produto, produtoNovo, "codigo");
		return repository.save(produtoNovo);
	}

	private void retornaErroCasoEmpty(Optional<Produto> produtoSalvo) {
		if(produtoSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
	}
}
