package br.com.jediStore.jediStore.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.jediStore.jediStore.model.Usuario;
import br.com.jediStore.jediStore.repository.UserRepository;

public class AutenticacaoTokenFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UserRepository userRepo;
	
	public AutenticacaoTokenFilter(TokenService tokenService, UserRepository userRepo) {
		super();
		this.tokenService = tokenService;
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken( request );
		boolean isValido = tokenService.isTokenValido( token );
		
		if(isValido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		long idUser = tokenService.getIdUSer( token );
		Usuario usuario = userRepo.findById(idUser).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);		
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if( token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			
			return null;
		}
		else {
			return token.substring(7, token.length());
		}
	}




}
