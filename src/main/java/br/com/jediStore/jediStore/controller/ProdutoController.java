package br.com.jediStore.jediStore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.jediStore.jediStore.dto.ProdutoDto;
import br.com.jediStore.jediStore.model.Produto;
import br.com.jediStore.jediStore.repository.ProdutoRepository;


@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private ProdutoDto produtoDto;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> detalhar(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepo.findById(id);
		if( produto.isPresent())
			return ResponseEntity.ok( new ProdutoDto(produto.get()));
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping
	@Cacheable( value = "listaDeProdutos")
	public Page<ProdutoDto> listaProduto( @RequestParam(required = false) String nomeProduto,
	@PageableDefault( sort="id", direction = Direction.DESC )Pageable paginacao ){
		
		Page<Produto> produtos;
		
		if( nomeProduto == null )
			produtos = produtoRepo.findAll(paginacao);
		else
			produtos = produtoRepo.findByNome( nomeProduto, paginacao );
		
		return ProdutoDto.converter(produtos);
	}
	
}
