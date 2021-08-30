package com.lojaapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Estoque;
import com.lojaapi.repository.EstoqueRepository;

@Service
public class EstoqueService {
	@Autowired
	private EstoqueRepository repository;

	public Estoque buscarEstoquePeloCodigo(Long codigo) {
		Optional<Estoque> estoqueSalvo = repository.findById(codigo);
		
		if(estoqueSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Estoque estoqueNovo = estoqueSalvo.get();
		return estoqueNovo;
	}
	
	public Estoque atualizarValorDeVenda(Long codigo, double valorDeVenda) {
		Optional<Estoque> estoqueSalvo = repository.findById(codigo);
		
		if(estoqueSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Estoque estoqueNovo = estoqueSalvo.get();
		estoqueNovo.setValorDeVenda(valorDeVenda);
		return repository.save(estoqueNovo);
	}

}
