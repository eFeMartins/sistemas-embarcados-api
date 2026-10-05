package br.edu.uva.sistemasembarcados.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	public Optional<Subject> findByCode(String code);
}
