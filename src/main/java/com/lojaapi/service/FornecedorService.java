package com.lojaapi.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Fornecedor;
import com.lojaapi.repository.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	private FornecedorRepository repository;
	
	public Fornecedor buscarFornecedorPeloCodigo(Long codigo) {
		Optional<Fornecedor> fornecedorSalvo = repository.findById(codigo);
		
		if(fornecedorSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Fornecedor fornecedorNovo = fornecedorSalvo.get();
		return fornecedorNovo;
	}
	
	public Fornecedor atualizar(Long codigo, Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorSalvo = repository.findById(codigo);
		
		if(fornecedorSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Fornecedor fornecedorNovo = fornecedorSalvo.get();
		BeanUtils.copyProperties(fornecedor, fornecedorNovo, "codigo");
		return repository.save(fornecedorNovo);
	}
}
