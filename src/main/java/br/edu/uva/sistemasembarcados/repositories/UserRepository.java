package br.edu.uva.sistemasembarcados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
