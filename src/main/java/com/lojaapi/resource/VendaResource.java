package com.lojaapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lojaapi.event.RecursoCriadoEvent;
import com.lojaapi.exceptionhandler.LojaApiExceptionHandler.Erro;
import com.lojaapi.model.Estoque;
import com.lojaapi.model.Venda;
import com.lojaapi.model.enums.StatusVenda;
import com.lojaapi.repository.VendaRepository;
import com.lojaapi.service.VendaService;
import com.lojaapi.service.exception.EstoqueInexistenteException;

@RestController
@RequestMapping("/vendas")
public class VendaResource {
	
	@Autowired
	private VendaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private MessageSource message;
	

	@GetMapping
	public Page<Venda> listarVendas(Pageable pageable){
		return repository.listar(pageable);
	}
	
	@GetMapping("/produtos")
	public ResponseEntity<List<Estoque>> listarProdutos(){
		List<Estoque> estoque = vendaService.listarProdutos();
		return ResponseEntity.ok().body(estoque);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Venda> criar(@Validated @RequestBody Venda venda, HttpServletResponse response){
		Venda vendaSalva = vendaService.salvar(venda);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}
	
	@PutMapping("/{codigo}/statusVenda")
	public ResponseEntity<Venda> atualizarStatusDaVenda(@PathVariable Long codigo, @RequestBody StatusVenda status){
		Venda vendaSalva = vendaService.atualizarStatusDaVenda(codigo, status);
		return ResponseEntity.ok(vendaSalva);
	}
	
	@ExceptionHandler({ EstoqueInexistenteException.class })
	public ResponseEntity<Object> handleEstoqueInexistenteException(EstoqueInexistenteException ex){	
		String msgUsuario = message.getMessage("estoque.inexistente", null, LocaleContextHolder.getLocale());
		String msgDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
