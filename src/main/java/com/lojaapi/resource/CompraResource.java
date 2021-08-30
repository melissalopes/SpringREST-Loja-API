package com.lojaapi.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lojaapi.event.RecursoCriadoEvent;
import com.lojaapi.model.Compra;
import com.lojaapi.repository.CompraRepository;
import com.lojaapi.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraResource {
	@Autowired
	private CompraRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CompraService compraService;
	
	@GetMapping
	public Page<Compra> listar(Pageable pageable){
		return repository.listar(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Compra> criar(@Validated @RequestBody Compra compra, HttpServletResponse response){
		Compra compraSalva = compraService.salvar(compra);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, compraSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
	}
	
}
