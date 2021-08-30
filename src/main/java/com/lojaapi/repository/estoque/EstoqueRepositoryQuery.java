package com.lojaapi.repository.estoque;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lojaapi.model.Estoque;

public interface EstoqueRepositoryQuery {
	Page<Estoque> listar(Pageable pageable);
}
