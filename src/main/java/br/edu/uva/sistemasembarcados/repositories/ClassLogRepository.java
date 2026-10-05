package br.edu.uva.sistemasembarcados.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.ClassLog;

public interface ClassLogRepository extends JpaRepository<ClassLog, Long>{
	public List<ClassLog> findByScheduleId(Long id);
	public Optional<ClassLog> findByScheduleIdAndTimestampBetween(Long scheduleId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
