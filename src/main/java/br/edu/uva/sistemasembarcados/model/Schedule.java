package br.edu.uva.sistemasembarcados.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.uva.sistemasembarcados.model.enums.DayOfWeek;

@Entity
@Table(name = "tb_schedule")
public class Schedule implements Serializable{
	private static final Long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek dayOfWeek;
	
	private LocalTime startTime;
	private LocalTime endTime;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private User teacher;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom usualRoom;
	
	public Schedule() {

	}
	public Schedule(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, User teacher, Subject subject,
			Classroom usualRoom) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.teacher = teacher;
		this.subject = subject;
		this.usualRoom = usualRoom;
	}
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Classroom getUsualRoom() {
		return usualRoom;
	}
	public void setUsualRoom(Classroom usualRoom) {
		this.usualRoom = usualRoom;
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
		Schedule other = (Schedule) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", teacher=" + teacher + ", subject=" + subject + ", usualRoom=" + usualRoom
				+ "]";
	}

	
	
	
}
