package com.lojaapi.repository.estoque;

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

import com.lojaapi.model.Estoque;

public class EstoqueRepositoryImpl implements EstoqueRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Estoque> listar(Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Estoque> criteria = builder.createQuery(Estoque.class);
		Root<Estoque> root = criteria.from(Estoque.class);
		Predicate[] predicates = criarRestricoes(builder, root);
		criteria.where(predicates);
		TypedQuery<Estoque> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total());
	}
	
	private Predicate[] criarRestricoes(CriteriaBuilder builder,
			Root<Estoque> root) {	
		List<Predicate> predicates = new ArrayList<>();
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Estoque> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Estoque> root = criteria.from(Estoque.class);
		Predicate[] predicates = criarRestricoes(builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}
