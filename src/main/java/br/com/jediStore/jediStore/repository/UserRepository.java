package br.com.jediStore.jediStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jediStore.jediStore.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail( String email );
}
