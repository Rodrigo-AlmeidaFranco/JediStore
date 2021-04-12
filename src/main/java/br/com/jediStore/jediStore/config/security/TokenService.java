package br.com.jediStore.jediStore.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import br.com.jediStore.jediStore.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class TokenService {
	
	@Value("${jedi.jwt.expiration}")
	private String exp;
	
	@Value("${jedi.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication auth) {
		Usuario logado = (Usuario) auth.getPrincipal();
		Date now = new Date( );
		Date dataExp = new Date( now.getTime()+Long.parseLong(exp) );
		return Jwts.builder()
				.setIssuer("App Forúm")
				.setSubject(logado.getId().toString())
				.setIssuedAt(now)
				.setExpiration(dataExp)
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public long getIdUSer(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
