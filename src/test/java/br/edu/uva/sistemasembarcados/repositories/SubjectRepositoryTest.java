package br.edu.uva.sistemasembarcados.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.uva.sistemasembarcados.model.Subject;

@DataJpaTest
public class SubjectRepositoryTest {
	
	@Autowired
	private SubjectRepository repository;
	
	@Test
	public void searchCode() {
		Subject subject = new Subject("A101", "XID1");
		
		subject = repository.save(subject);
		
		assertThat(repository.findByCode(subject.getCode())).isPresent();
	}
}
