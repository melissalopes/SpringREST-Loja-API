package com.lojaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Cliente;
import com.lojaapi.repository.cliente.ClienteRepositoryQuery;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{
	Cliente findByEmail(String email);
}
