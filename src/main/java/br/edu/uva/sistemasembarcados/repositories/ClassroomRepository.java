package br.edu.uva.sistemasembarcados.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Long>{
	public Optional<Classroom> findByRoomNumber(String roomNumber);
	public Optional<Classroom> findByBoardId(String boardId);
}
