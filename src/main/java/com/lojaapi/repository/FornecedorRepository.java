package com.lojaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Fornecedor;
import com.lojaapi.repository.fornecedor.FornecedorRepositoryQuery;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery{


}
