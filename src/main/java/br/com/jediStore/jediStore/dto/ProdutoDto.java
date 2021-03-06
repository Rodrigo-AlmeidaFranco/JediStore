package br.com.jediStore.jediStore.dto;

import org.springframework.data.domain.Page;

import br.com.jediStore.jediStore.model.Produto;

public class ProdutoDto {
	
	public ProdutoDto( Produto produto ) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
	}
	
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	
	public static Page<ProdutoDto> converter( Page<Produto> produtos ){
		return produtos.map(ProdutoDto::new);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
	
}
