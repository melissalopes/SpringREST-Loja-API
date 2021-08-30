package com.lojaapi.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lojaapi.event.RecursoCriadoEvent;
import com.lojaapi.model.Fornecedor;
import com.lojaapi.repository.FornecedorRepository;
import com.lojaapi.repository.filter.FornecedorFilter;
import com.lojaapi.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {
	@Autowired
	private FornecedorRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@GetMapping
	public Page<Fornecedor> listar(FornecedorFilter fornecedorFilter, Pageable pageable){
		return repository.filtrar(fornecedorFilter, pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Fornecedor> criar(@Validated @RequestBody Fornecedor fornecedor, HttpServletResponse response){
		Fornecedor fornecedorSalvo = repository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Fornecedor> buscarPeloCodigo(@PathVariable Long codigo){
		Fornecedor fornecedorSalvo = fornecedorService.buscarFornecedorPeloCodigo(codigo);
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Fornecedor> atualizar(@PathVariable Long codigo, @Validated @RequestBody Fornecedor fornecedor){
		Fornecedor fornecedorSalvo = fornecedorService.atualizar(codigo, fornecedor);
		return ResponseEntity.ok(fornecedorSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}
}
