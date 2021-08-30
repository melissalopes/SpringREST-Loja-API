package com.lojaapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lojaapi.model.enums.StatusVenda;

@Entity
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	@OneToOne
	private Cliente cliente;
	
	@Enumerated(EnumType.STRING)
	private StatusVenda statusVenda;

	@NotNull
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<ItensDaVenda> itensDaVenda = new ArrayList<ItensDaVenda>();

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}

	public List<ItensDaVenda> getItensDaVenda() {
		return itensDaVenda;
	}

	public void setItensDaVenda(List<ItensDaVenda> itensDaVenda) {
		this.itensDaVenda = itensDaVenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((itensDaVenda == null) ? 0 : itensDaVenda.hashCode());
		result = prime * result + ((statusVenda == null) ? 0 : statusVenda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (itensDaVenda == null) {
			if (other.itensDaVenda != null)
				return false;
		} else if (!itensDaVenda.equals(other.itensDaVenda))
			return false;
		if (statusVenda != other.statusVenda)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Compra bem sucessida <3 !"
				+ "\nCódigo = " + getCodigo() 
				+ "\nNome do Cliente = " + getCliente().getNome()
				+ "\nStatus da venda = " + getStatusVenda() 
				+ "\nItens Comprados = "
				+ getItensDaVenda().toString();
	}
	
	
	
}
