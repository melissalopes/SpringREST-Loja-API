package com.lojaapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lojaapi.model.Estoque;
import com.lojaapi.model.ItensDaVenda;
import com.lojaapi.model.Venda;
import com.lojaapi.model.enums.StatusVenda;
import com.lojaapi.repository.EstoqueRepository;
import com.lojaapi.repository.ItensDaVendaRepository;
import com.lojaapi.repository.VendaRepository;
import com.lojaapi.service.exception.EstoqueInexistenteException;

@Service
public class VendaService {

	@Autowired
	private VendaRepository repository;
	
	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	public ItensDaVendaRepository itensRepository;
	
	public List<Estoque> listarProdutos() {
		List<Estoque> estoque = estoqueRepository.findAll();
		List<Estoque> estoqueSalvo = new ArrayList<>();
		
		for(Estoque novoEstoque : estoque) {
			if(!(novoEstoque.getValorDeVenda() <= 0) && !(novoEstoque.getQuantidade() <= 0)) {
				estoqueSalvo.add(novoEstoque);
			}
		}
		
		return estoqueSalvo;
	}
	
	public Venda salvar(Venda venda) {
		
		for(ItensDaVenda novaVenda: venda.getItensDaVenda()) {
			Long codigo = estoqueRepository.findById(novaVenda.getEstoque().getCodigo()).get().getCodigo();
            Estoque estoque = estoqueRepository.findProdutoByCodigo(codigo);
            
            if(estoque.getQuantidade() - novaVenda.getQuantidade() <= 0) {
    			throw new EstoqueInexistenteException();
    		}
            
            estoque.setQuantidade(estoque.getQuantidade() - novaVenda.getQuantidade());
            venda.setStatusVenda(StatusVenda.PENDENTE);
            estoqueRepository.save(estoque);
		}
		
		return repository.save(venda);
	}

	public Venda atualizarStatusDaVenda(Long codigo, StatusVenda status) {
		Optional<Venda> vendaSalva = repository.findById(codigo);
		
		if(vendaSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Venda vendaNova = vendaSalva.get();
		vendaNova.setStatusVenda(status);
		return repository.save(vendaNova);
	}

}
