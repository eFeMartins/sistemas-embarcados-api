package br.edu.uva.sistemasembarcados.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_classlog")
public class ClassLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date;
	private LocalDateTime readTimeStamp;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
	
	@ManyToOne
	@JoinColumn(name = "actual_classroom_id")
	private Classroom actualClassRoom;
	
	public ClassLog() {
	}
	public ClassLog(LocalDate date, LocalDateTime readTimeStamp, Schedule schedule, Classroom actualClassRoom) {
		this.date = date;
		this.readTimeStamp = readTimeStamp;
		this.schedule = schedule;
		this.actualClassRoom = actualClassRoom;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalDateTime getReadTimeStamp() {
		return readTimeStamp;
	}
	public void setReadTimeStamp(LocalDateTime readTimeStamp) {
		this.readTimeStamp = readTimeStamp;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Classroom getActualClassRoom() {
		return actualClassRoom;
	}
	public void setActualClassRoom(Classroom actualClassRoom) {
		this.actualClassRoom = actualClassRoom;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassLog other = (ClassLog) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "classLog [id=" + id + ", schedule=" + schedule + ", actualClassRoom=" + actualClassRoom + "]";
	}

}
