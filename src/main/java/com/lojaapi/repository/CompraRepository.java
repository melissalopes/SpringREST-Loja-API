package com.lojaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Compra;
import com.lojaapi.repository.compra.CompraRepositoryQuery;

public interface CompraRepository extends JpaRepository<Compra, Long>, CompraRepositoryQuery{

}
