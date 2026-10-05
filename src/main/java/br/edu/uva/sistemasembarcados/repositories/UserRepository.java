package br.edu.uva.sistemasembarcados.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByRfidTag(String rfidTag);
	public Optional<User> findByEmail(String email);
}
