package br.com.jediStore.jediStore.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Venda {
	
	@Getter
	@Setter
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	@Getter
	@Setter
	@OneToMany
	private List<Produto> produto = new ArrayList<Produto>();
	
	
}
