package com.lojaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	public List<Produto> findByNome(String nome);

}
