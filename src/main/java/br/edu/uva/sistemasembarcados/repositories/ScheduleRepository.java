package br.edu.uva.sistemasembarcados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uva.sistemasembarcados.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

}
