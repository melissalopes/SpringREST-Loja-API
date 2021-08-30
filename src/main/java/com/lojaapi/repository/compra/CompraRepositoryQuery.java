package com.lojaapi.repository.compra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lojaapi.model.Compra;

public interface CompraRepositoryQuery {
	public Page<Compra> listar(Pageable pageable);
}
