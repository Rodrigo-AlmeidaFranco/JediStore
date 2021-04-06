package br.com.jediStore.jediStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Caracteristicas {
	
	@Getter
	@Setter
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	@Getter
	@Setter
	@ManyToOne
	private Produto produto;
	
	@Getter
	@Setter
	private String cor;
	
	@Getter
	@Setter
	private String modelo;
	
	@Getter
	@Setter
	private String tamanho;
	
	@Getter
	@Setter
	private String anoFabricacao;
	
	@Getter
	@Setter
	private String marca;
}
