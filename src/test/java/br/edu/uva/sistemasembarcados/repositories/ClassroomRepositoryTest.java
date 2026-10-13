package br.edu.uva.sistemasembarcados.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.uva.sistemasembarcados.model.Classroom;

@DataJpaTest
public class ClassroomRepositoryTest {

	@Autowired
	private ClassroomRepository repository;
	
	@Test
	public void savingAndFindingRoom() {
		Classroom classroom1 = new Classroom("A101", "XID1");
		classroom1 = repository.save(classroom1);
		
		assertThat(repository.findByRoomNumber(classroom1.getRoomNumber())).isPresent();
	}
	
	@Test
	public void savingAndFindingBoard() {
		Classroom classroom1 = new Classroom("A101", "XID1");
		classroom1 = repository.save(classroom1);
		
		assertThat(repository.findByBoardId(classroom1.getBoardId())).isPresent();
	}
//	findByRoomNumber(String roomNumber);
//	findByBoardId(String boardId);
}
