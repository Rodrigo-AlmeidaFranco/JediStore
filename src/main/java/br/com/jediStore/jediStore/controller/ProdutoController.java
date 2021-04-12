package br.com.jediStore.jediStore.controller;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.jediStore.jediStore.dto.ProdutoDto;
import br.com.jediStore.jediStore.form.AtualizaProdutoForm;
import br.com.jediStore.jediStore.form.ProdutoForm;
import br.com.jediStore.jediStore.model.Produto;
import br.com.jediStore.jediStore.repository.ProdutoRepository;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
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
	
	@PostMapping
	@Transactional
	@CacheEvict( value = "listaDeProdutos", allEntries = true )
	public ResponseEntity< ProdutoDto > cadastrar( @RequestBody @Valid ProdutoForm form, UriComponentsBuilder uri ) {
		Produto produto = form.converter( produtoRepo );
		produtoRepo.save( produto );
		
		return ResponseEntity.created(uri.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri()).body( new ProdutoDto(produto));
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict( value = "listaDeProdutos", allEntries = true )
	public ResponseEntity< ProdutoDto > atualizar( @PathVariable Long id, @RequestBody @Valid AtualizaProdutoForm form  ) {
		Produto produto = form.atualizar(id,produtoRepo);
		return ResponseEntity.ok( new ProdutoDto(produto));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict( value = "listaDeTopicos", allEntries = true )
	public ResponseEntity<?> Deletar(@PathVariable Long id) {
		produtoRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
}
