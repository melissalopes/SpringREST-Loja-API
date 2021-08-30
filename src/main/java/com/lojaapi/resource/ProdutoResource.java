package com.lojaapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lojaapi.event.RecursoCriadoEvent;
import com.lojaapi.model.Produto;
import com.lojaapi.repository.ProdutoRepository;
import com.lojaapi.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<Produto>> listar(){
		List<Produto> produtos = produtoService.findAll();
		return ResponseEntity.ok().body(produtos);
	}
	
	@GetMapping("/asc")
	public ResponseEntity<Page<Produto>> listarEmOrdemCrescentePorNome(
			@PageableDefault(page = 0, size = 24, sort = "nome", direction = Direction.ASC) 
			Pageable pageable){
		Page<Produto> produtos = produtoService.findPage(pageable);
		return ResponseEntity.ok().body(produtos);
	}
	
	@GetMapping("/desc")
	public ResponseEntity<Page<Produto>> listarEmOrdemDescrescentePorNome(
			@PageableDefault(page = 0, size = 24, sort = "nome", direction = Direction.DESC) 
			Pageable pageable){
		Page<Produto> produtos = produtoService.findPage(pageable);
		return ResponseEntity.ok().body(produtos);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Produto> criar(@Validated @RequestBody Produto produto, HttpServletResponse response){
		Produto produtoSalvo = repository.save(produto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> buscarPeloCodigo(@PathVariable Long codigo){
		Produto produtoSalvo = produtoService.buscarProdutoPeloCodigo(codigo);
		return ResponseEntity.ok(produtoSalvo);
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> buscarPeloNome(@PathVariable String nome){
		List<Produto> produtos = produtoService.buscarProdutoPeloNome(nome);
		return ResponseEntity.ok(produtos);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigo, @Validated @RequestBody Produto produto){
		Produto produtoSalvo = produtoService.atualizar(codigo, produto);
		return ResponseEntity.ok(produtoSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}
}
