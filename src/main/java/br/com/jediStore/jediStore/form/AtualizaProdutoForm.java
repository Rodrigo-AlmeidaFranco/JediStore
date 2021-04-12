package br.com.jediStore.jediStore.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.jediStore.jediStore.model.Produto;
import br.com.jediStore.jediStore.repository.ProdutoRepository;

public class AtualizaProdutoForm {
	
	@NotNull @NotEmpty @Length( min = 5)
	private String nome;
	@NotNull @NotEmpty @Length( min = 10)
	private String descricao;
	@NotNull @NotEmpty 
	private String img;
	@NotNull @NotEmpty 
	private Double preco;
	
	
	public Produto atualizar(Long id, ProdutoRepository produtoRepo) {
		Produto produto = produtoRepo.getOne(id);
		produto.setNome(this.nome);
		produto.setPreco(this.preco);
		produto.setDescricao(this.descricao);
		produto.setImg(img);
		return produto;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
