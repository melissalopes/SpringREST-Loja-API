package com.lojaapi.repository.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lojaapi.model.Fornecedor;
import com.lojaapi.repository.filter.FornecedorFilter;

public interface FornecedorRepositoryQuery {
	Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilter, Pageable pageable);
}
