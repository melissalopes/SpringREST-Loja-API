package com.lojaapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojaapi.model.Estoque;
import com.lojaapi.repository.EstoqueRepository;
import com.lojaapi.service.EstoqueService;

@RestController
@RequestMapping("/estoque")
public class EstoqueResource {
	
	@Autowired
	private EstoqueRepository repository;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@GetMapping
	public Page<Estoque> listar(Pageable pageable){
		return repository.listar(pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Estoque> buscarPeloCodigo(@PathVariable Long codigo){
		Estoque estoqueSalvo = estoqueService.buscarEstoquePeloCodigo(codigo);
		return ResponseEntity.ok(estoqueSalvo);
	}
	
	@PutMapping("/{codigo}/valor")
	public ResponseEntity<Estoque> atualizarValorDeVenda(@PathVariable Long codigo, @RequestBody double valorDeVenda){
		Estoque estoqueSalvo = estoqueService.atualizarValorDeVenda(codigo, valorDeVenda);
		return ResponseEntity.ok(estoqueSalvo);
	}
	
}
