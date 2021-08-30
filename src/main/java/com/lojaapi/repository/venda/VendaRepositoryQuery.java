package com.lojaapi.repository.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lojaapi.model.Venda;

public interface VendaRepositoryQuery {
	public Page<Venda> listar(Pageable pageable);
}
