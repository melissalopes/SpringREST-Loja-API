package com.lojaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Estoque;
import com.lojaapi.repository.estoque.EstoqueRepositoryQuery;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>, EstoqueRepositoryQuery{

	Estoque findProdutoByCodigo(Long codigo);

	List<Estoque> findEstoqueByQuantidade(int quantidade);

}
