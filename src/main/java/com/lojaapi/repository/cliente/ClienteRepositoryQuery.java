package com.lojaapi.repository.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lojaapi.model.Cliente;
import com.lojaapi.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {

	Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);
}
