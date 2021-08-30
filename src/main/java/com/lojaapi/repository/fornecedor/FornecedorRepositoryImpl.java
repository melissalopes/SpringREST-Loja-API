package com.lojaapi.repository.fornecedor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.lojaapi.model.Fornecedor;
import com.lojaapi.repository.filter.FornecedorFilter;


public class FornecedorRepositoryImpl implements FornecedorRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Fornecedor> filtrar(FornecedorFilter fornecedorFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> criteria = builder.createQuery(Fornecedor.class);
		Root<Fornecedor> root = criteria.from(Fornecedor.class);
		Predicate[] predicates = criarRestricoes(fornecedorFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Fornecedor> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(fornecedorFilter));
	}
	
	private Predicate[] criarRestricoes(FornecedorFilter fornecedorFilter, CriteriaBuilder builder,
			Root<Fornecedor> root) {	
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(fornecedorFilter.getEmail())) {
			predicates.add(builder.like(
					builder.lower(root.get("email")), 
					"%" + fornecedorFilter.getEmail().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(fornecedorFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), 
					"%" + fornecedorFilter.getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Fornecedor> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(FornecedorFilter fornecedorFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Fornecedor> root = criteria.from(Fornecedor.class);
		Predicate[] predicates = criarRestricoes(fornecedorFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}
