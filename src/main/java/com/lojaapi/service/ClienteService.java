package com.lojaapi.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Cliente;
import com.lojaapi.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	//Fazer cadastro de clientes de um jeito que eles possam se logar sozinhos
	public Cliente salvar(Cliente cliente) {		
		return repository.save(cliente);
	}
	
	public Cliente buscarClientePeloCodigo(Long codigo) {
		Optional<Cliente> clienteSalvo = repository.findById(codigo);
		
		if(clienteSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Cliente clienteNovo = clienteSalvo.get();
		return clienteNovo;
	}
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Optional<Cliente> clienteSalvo = repository.findById(codigo);
		
		if(clienteSalvo.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Cliente clienteNovo = clienteSalvo.get();
		BeanUtils.copyProperties(cliente, clienteNovo, "codigo");
		return repository.save(clienteNovo);
	}

}
