package com.lojaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lojaapi.model.Venda;
import com.lojaapi.repository.venda.VendaRepositoryQuery;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery{

}
