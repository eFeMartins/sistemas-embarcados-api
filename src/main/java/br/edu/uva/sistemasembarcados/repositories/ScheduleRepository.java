package br.edu.uva.sistemasembarcados.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	public List<Schedule> findByUserId(Long id);
	public List<Schedule> findByUsualClassroomId(Long usualClassroomId);
	public List<Schedule> findBySubjectId(Long subjectId);
}
